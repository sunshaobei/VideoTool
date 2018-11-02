package com.ccee.videotool.event;

public interface AllvideoListener {

    void allVideo(AllVideo allVideo);

    class AllVideo{
        public AllVideo(int count) {
            this.count = count;
        }

        private int count;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
