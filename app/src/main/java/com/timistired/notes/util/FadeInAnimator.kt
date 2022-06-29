package com.timistired.notes.util

import android.animation.Animator
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Lifecycle.Event
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable

/**
 * Lifecycle aware animation util class that fades in views.
 * @param targets the views to fade in. Views with alpha equal to 1 will be ignored.
 * @param duration the duration of each animation in ms.
 * @param lifecycleOwner the current [LifecycleOwner] to consider when animating views.
 * Animations will happen on resume.
 * */
class FadeInAnimator(
    targets: List<View>,
    private val duration: Long,
    private val lifecycleOwner: LifecycleOwner
) {
    private var animationDisposable: Disposable? = null
    private val queue: MutableSet<View> = mutableSetOf()

    init {
        queue.addAll(targets)

        lifecycleOwner.lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Event) {
                when (event) {
                    Event.ON_RESUME -> animate()
                    Event.ON_PAUSE, Event.ON_STOP, Event.ON_DESTROY -> stop()
                    else -> { // ignore
                    }
                }
            }
        })
    }

    private fun animate() {
        val pendingItems = queue.filter { it.alpha != 1F }

        if (pendingItems.isEmpty()) {
            return
        }

        val animationObservable = Observable
            .fromIterable(pendingItems)
            .concatMapCompletable { it.fadeIn() }

        if (lifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) {
            animationDisposable?.dispose()
            animationDisposable = animationObservable.subscribe()
        }
    }

    private fun stop() {
        animationDisposable?.dispose()
        animationDisposable = null
    }

    private fun View.fadeIn(): Completable = Completable.create { emitter ->
        animate()
            .alpha(1F)
            .setDuration(duration)
            .setListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {}
                override fun onAnimationCancel(animation: Animator?) {}
                override fun onAnimationRepeat(animation: Animator?) {}
                override fun onAnimationEnd(animation: Animator?) {
                    emitter.onComplete()
                }
            })
    }
}