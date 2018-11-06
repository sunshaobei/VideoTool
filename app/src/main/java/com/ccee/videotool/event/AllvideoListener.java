package com.ccee.videotool.event;

public interface AllvideoListener {

    void allVideo(AllVideo allVideo);

    class AllVideo{
        private  Integer type;

        public AllVideo(int count, Integer type) {
            this.count = count;
            this.type = type;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
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
