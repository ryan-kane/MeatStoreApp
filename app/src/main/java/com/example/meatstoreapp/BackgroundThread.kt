package com.example.meatstoreapp

import java.util.concurrent.Executor
import kotlin.concurrent.thread

class BackgroundThread() : Executor{

    override fun execute(command: Runnable) {
        /**
         * @param command a runnable task to be executed
         */
        Thread(command).start()
    }
}