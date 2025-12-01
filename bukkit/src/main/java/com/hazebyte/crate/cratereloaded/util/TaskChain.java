package com.hazebyte.crate.cratereloaded.util;

import com.hazebyte.crate.cratereloaded.CorePlugin;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

/**
 * Facilitates Control Flow for the Bukkit Scheduler to easily jump between Async and Sync tasks
 * without deeply nested callbacks, passing the response of the previous effectTask to the next
 * effectTask to use.
 *
 * <p>Usage example: TaskChain.newChain() .add(new TaskChain.AsyncTask {}) .add(new TaskChain.Task
 * {}) .add(new AsyncTask {}) .execute();
 */
public class TaskChain {
    /**
     * Utility helpers for Task returns. Changes the behavior of the Chain when these are returned.
     */
    // Tells a effectTask it will perform call back later.
    public static final Object ASYNC = new Object();
    // Abort executing the chain
    public static final Object ABORT = new Object();
    private final Plugin plugin;
    /**
     * =============================================================================================
     */
    ConcurrentLinkedQueue<BaseTask> chainQueue = new ConcurrentLinkedQueue<>();

    boolean executed = false;
    Object previous = null;
    boolean async;

    public TaskChain() {
        this.plugin = CorePlugin.getPlugin();
        this.async = !Bukkit.isPrimaryThread();
    }
    /**
     * =============================================================================================
     */

    /**
     * Starts a new chain.
     *
     * @return
     */
    public static TaskChain newChain() {
        return new TaskChain();
    }

    /**
     * Adds a step to the chain execution. Async*Task will run off of main thread, *Task will run sync
     * with main thread
     *
     * @param task
     * @return
     */
    public TaskChain add(BaseTask task) {
        synchronized (this) {
            if (executed) {
                throw new RuntimeException("TaskChain is executing");
            }
        }

        chainQueue.add(task);
        return this;
    }

    /** Finished adding tasks, begins executing them. */
    public void execute() {
        synchronized (this) {
            if (executed) {
                throw new RuntimeException("Already executed");
            }
            executed = true;
        }
        nextTask();
    }

    /** Fires off the next effectTask, and switches between Async/Sync as necessary. */
    private void nextTask() {
        final TaskChain chain = this;
        final BaseTask task = chainQueue.poll();
        if (task == null) {
            // done!
            return;
        }
        if (task.async) {
            if (async) {
                task.run(this);
            } else {
                Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                    chain.async = true;
                    task.run(chain);
                });
            }
        } else {
            if (async) {
                Bukkit.getScheduler().runTask(plugin, () -> {
                    chain.async = false;
                    task.run(chain);
                });
            } else {
                task.run(this);
            }
        }
    }

    /**
     * Provides foundation of a effectTask with what the previous effectTask type should return to
     * pass to this and what this effectTask will return.
     *
     * @param <R> Return Type
     * @param <A> Argument Type Expected
     */
    private abstract static class BaseTask<R, A> {
        TaskChain chain = null;
        boolean async = false;
        boolean executed = false;

        /**
         * Task Type classes will implement this
         *
         * @param arg
         * @return
         */
        protected abstract R runTask(A arg);

        /**
         * Called internally by Task Chain to facilitate executing the effectTask and then the next
         * effectTask.
         *
         * @param chain
         */
        private void run(TaskChain chain) {
            final Object arg = chain.previous;
            chain.previous = null;
            this.chain = chain;
            R ret = this.runTask((A) arg);
            if (chain.previous == null) {
                chain.previous = ret;
            }
            if (chain.previous != ASYNC && chain.previous != ABORT) {
                synchronized (this) {
                    executed = true;
                }
                chain.nextTask();
            }
        }

        /** Tells the TaskChain to abort processing any more tasks. */
        public R abort() {
            chain.previous = ABORT;
            return null;
        }

        /**
         * Tells the TaskChain you will manually invoke the next effectTask manually using
         * effectTask.next(response);
         */
        public R async() {
            chain.previous = ASYNC;
            return null;
        }

        /**
         * Only to be used when paired with return this.async(); Must be called to execute the next
         * effectTask.
         *
         * <p>To be used inside a callback of another operation that is performed async.
         *
         * @param resp
         */
        public void next(R resp) {
            synchronized (this) {
                if (executed) {
                    throw new RuntimeException("This effectTask has already been executed. return this.async()");
                }
            }
            chain.async = !Bukkit.isPrimaryThread(); // We don't know where the effectTask called this from.
            chain.previous = resp;
            chain.nextTask();
        }
    }

    /**
     * General abstract classes to be used for various tasks in the chain.
     *
     * <p>First Tasks are for when you do not have or do not care about the return value of a previous
     * effectTask.
     *
     * <p>Last Tasks are for when you do not need to use a return type.
     *
     * <p>A Generic effectTask simply does not care about Previous Return or return anything itself.
     *
     * <p>Async Tasks will not run on the Minecraft Thread and should not use the Bukkit API unless it
     * is thread safe.
     */
    public abstract static class Task<R, A> extends BaseTask<R, A> {
        protected abstract R run(A arg);

        @Override
        protected R runTask(A arg) {
            return run(arg);
        }
    }

    public abstract static class GenericTask extends BaseTask<Object, Object> {
        protected abstract void run();

        public void next() {
            next(null);
        }

        @Override
        protected Object runTask(Object arg) {
            run();
            return null;
        }
    }

    public abstract static class FirstTask<R> extends BaseTask<R, Object> {
        protected abstract R run();

        @Override
        protected R runTask(Object arg) {
            return run();
        }
    }

    public abstract static class LastTask<A> extends BaseTask<Object, A> {
        protected abstract void run(A arg);

        public void next() {
            next(null);
        }

        @Override
        protected Object runTask(A arg) {
            run(arg);
            return null;
        }
    }

    // Async helpers
    public abstract static class AsyncTask<R, A> extends Task<R, A> {
        {
            async = true;
        }
    }

    public abstract static class AsyncGenericTask extends GenericTask {
        {
            async = true;
        }
    }

    public abstract static class AsyncFirstTask<R> extends FirstTask<R> {
        {
            async = true;
        }
    }

    public abstract static class AsyncLastTask<A> extends LastTask<A> {
        {
            async = true;
        }
    }
}
