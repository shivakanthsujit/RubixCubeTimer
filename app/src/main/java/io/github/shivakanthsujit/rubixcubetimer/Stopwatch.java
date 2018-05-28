package io.github.shivakanthsujit.rubixcubetimer;

class Stopwatch {

        private long startTime = 0;
        private long stopTime = 0;
        public boolean running = false;


        public void start() {
            this.startTime = System.currentTimeMillis();
            this.running = true;
        }


        public void stop() {
            this.stopTime = System.currentTimeMillis();
            this.running = false;
        }

        public long getElapsedTime() {
            if (running) {
                return System.currentTimeMillis() - startTime;
            }
            return stopTime - startTime;
        }


        public long getElapsedTimeSecs() {
            if (running) {
                return ((System.currentTimeMillis() - startTime) / 1000);
            }
            return ((stopTime - startTime) / 1000);
        }


}
