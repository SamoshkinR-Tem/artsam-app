package com.artsam.presentation.utils

import android.os.Build
import androidx.annotation.RequiresApi
import io.reactivex.*
import io.reactivex.Observable
import io.reactivex.functions.Action
import io.reactivex.observables.GroupedObservable
import io.reactivex.subjects.AsyncSubject
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.ReplaySubject
import java.util.*
import java.util.concurrent.*


class RxExamples {

    companion object{

        fun skip(){
            Observable.range(1, 10)
                .contains(4)
                .subscribe(System.out::println) // print 1 2 3 4
        }

        fun runSubjectExamples() {
            val publishSubject = PublishSubject.create<Int>()
            publishSubject.onNext(0)
            publishSubject.onNext(1)
            publishSubject.onNext(2)
            publishSubject.subscribe { println("publishSubject: $it") }
            publishSubject.onNext(3)
            publishSubject.onNext(4)
            publishSubject.onNext(5)

            val replaySubject = ReplaySubject.create<Int>()
            replaySubject.onNext(0)
            replaySubject.onNext(1)
            replaySubject.onNext(2)
            replaySubject.onNext(3)
            replaySubject.subscribe { println("replaySubject: $it") }
            replaySubject.onNext(4)
            replaySubject.onNext(5)

            val replaySubject2 = ReplaySubject.createWithSize<Int>(2)
            replaySubject2.onNext(0)
            replaySubject2.onNext(1)
            replaySubject2.onNext(2)
            replaySubject2.onNext(3)
            replaySubject2.subscribe { println("replaySubject2: $it") }
            replaySubject2.onNext(4)
            replaySubject2.onNext(5)

            val behaviourSubject = BehaviorSubject.createDefault(-1)
            behaviourSubject.onNext(0)
            behaviourSubject.onNext(1)
            behaviourSubject.onNext(2)
            behaviourSubject.subscribe { println("behaviourSubject: $it") }
            behaviourSubject.onNext(3)
            behaviourSubject.onNext(4)
            behaviourSubject.onNext(5)

            val asyncSubject = AsyncSubject.create<Int>()
            asyncSubject.onNext(0)
            asyncSubject.onNext(1)
            asyncSubject.onNext(2)
            asyncSubject.subscribe { println("asyncSubject: $it") }
            asyncSubject.onNext(3)
            asyncSubject.onNext(4)
            asyncSubject.onNext(5)
            asyncSubject.onComplete()
        }

        @RequiresApi(Build.VERSION_CODES.N)
        fun runRXExample(){
            runCreateSequnce()
            runUseFlatMap()
            runUseGroupBy()
            useBlocking()
            useFlowable()
        }

        @RequiresApi(Build.VERSION_CODES.N)
        fun runCreateSequnce() {

            val list = Observable.fromArray(*arrayOf("Hello", "World", "from", "array"))
                .subscribe(System.out::print)

            val list2 = Observable.fromArray(arrayOf("Hello", "World", "from", "array"))
                .subscribe(System.out::print)

            // .fromCallable
            Observable.fromCallable(MyJob())
                .subscribe(System.out::println)

            // .fromAction
            val action = Action { println("Hello from action!") }
            Completable.fromAction(action)
                .subscribe()

            // .fromRunnable
            val runnable = Runnable { println("Hello from runnable!") }
            Completable.fromRunnable(runnable)
                .subscribe()

            // .fromFuture
            val executor: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
            val future = executor.schedule({ "Hello world!" }, 10, TimeUnit.SECONDS)

            Single.fromFuture(future)
                .subscribe({ println(it) }, { println(it.message) })

            // .create
            val handler = ObservableOnSubscribe { emitter: ObservableEmitter<String?> ->
                val future: Future<Any?> = executor.schedule<Any?>({
                    emitter.onNext("Hello")
                    emitter.onNext("World")
                    emitter.onNext("from")
                    emitter.onNext(".create()")
                    emitter.onComplete()
                    null
                }, 1, TimeUnit.SECONDS)
                emitter.setCancellable { future.cancel(false) }
            }

            Observable.create(handler)
                .subscribe(
                    { item -> System.out.print(item) },
                    { error -> error.printStackTrace() })
                { println("Done") }

            Thread.sleep(2000)
            executor.shutdown()

            // .generate
//            val startValue = 1
//            val incrementValue = 1
//            val flowable = Flowable.generate(Callable { startValue }, BiFunction { s: Int, emitter: Emitter<Int?> ->
//                val nextValue = s + incrementValue
//                emitter.onNext(nextValue)
//                nextValue
//            })
//            flowable.subscribe { value: Int? -> println(value) }

            // .never
            Observable.never<Int>()
                .subscribe(
                    { println("This should never be printed") },
                    { println("Or this") },
                    { println("Done will be printed") })

            // .range
            Observable.range(0, "greeting".length)
                .map { index -> "greeting".toCharArray()[index] }
                .subscribe({ println(it) })

            // @RequiresApi(Build.VERSION_CODES.N)
            tryWindow()

//            Observable.interval(1, TimeUnit.SECONDS)
//                .subscribe { time ->
//                if (time % 2 == 0L) {
//                    println("Tick")
//                } else {
//                    println("Tock")
//                }
//            }
        }

        @RequiresApi(Build.VERSION_CODES.N)
        private fun tryWindow() {
            Observable.range(1, 10)
                // Create windows containing at most 2 items, and skip 3 items before starting a new window.
                .window(2, 3)
                .flatMapSingle { window ->
                    window.map(java.lang.String::valueOf)
                        .reduce(StringJoiner(", ", "[", "]"), StringJoiner::add) }
                .subscribe(System.out::println)
        }

        fun runUseFlatMap() {
            val a = Observable.just("A", "B", "C")
                .flatMap { a ->
                    Observable.intervalRange(1, 3, 0, 1, TimeUnit.SECONDS)
                        .map { b: Long -> "($a, $b)" }
                        //.flatMap {  } // так можно делать только если третьему потоку нужно данные из первого потока (переменной "а")
                        .map { Pair(a, it) }
                }
                .flatMap { (a, b) ->
                    a // данные из первого потока
                    b // данные из второго потока
                    Observable.just("a: $a, b: $b")
                }
                .subscribe() { print("$it ") } // "it" is Sting

            Observable.just(Observable.just("a"), Observable.just("b"), Observable.just("c"))
                .flatMap {
                    it
                }
                .blockingSubscribe { print("$it ") }

            Observable.just("a", "b", "c")
                .flatMap {
                    Single.just("${it+3}").toObservable()
                }
                .blockingSubscribe { print("$it ") }

            Observable.just(2L, 1L, 3L)
                .flatMapCompletable { a ->
                    Completable.timer(a, TimeUnit.SECONDS)
                        .doOnComplete { println("Info: Processing of item \" + $a + \" completed") }
                }
                .doOnComplete { println("Info: Processing of all items completed") }
                .blockingAwait()

            val nestedCollections: List<Int> =
                listOf(listOf(1,2,3), listOf(5,4,3))
                    .flatten() //[1, 2, 3, 5, 4, 3]
            nestedCollections.forEach { println("$it") }
        }

        fun runUseGroupBy() {
            val animals = Observable.just("Tiger", "Elephant", "Cat", "Chameleon", "Frog", "Fish", "Turtle", "Flamingo")
            animals.groupBy({ animal -> animal[0] }) { obj ->
                obj.uppercase(Locale.getDefault())
            }
                .concatMapSingle { obj: GroupedObservable<Char, String> ->
                    obj.toList()
                }
                .subscribe(System.out::println)
        }

        fun useBlocking(): String {
            var result = ""
            Observable.just("Hello Vasya!")
                .delay(1, TimeUnit.SECONDS)
                .blockingSubscribe({
                    result = it
                })

            result = Observable.just("Hello Vasya!")
                .delay(1, TimeUnit.SECONDS)
                .blockingFirst()

            Observable.just("Vasya lapuh!")
                .delay(1, TimeUnit.SECONDS)
                .subscribe({
                    result = it
                })
            println("resuls is $result")
            return result
        }

        fun useFlowable(){
            Flowable.just("a", "b", "c")

            val sbj = PublishSubject.create<Int>()


            sbj.onNext(1)
            sbj.onNext(2)
            sbj.onNext(3)
            sbj.onNext(4)
            // sbj.toFlowable(BackpressureStrategy.LATEST)
            sbj.subscribe(System.out::println, Throwable::printStackTrace)
            sbj.onNext(5)
            sbj.onNext(6)
            sbj.onNext(7)
        }
    }
}

class MyJob : Callable<Int> {
    override fun call() = 42
}