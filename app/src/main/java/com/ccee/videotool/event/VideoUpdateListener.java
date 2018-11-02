package com.ccee.videotool.event;

public interface VideoUpdateListener {
    void onUpdate(UpdateVideo o);

    class UpdateVideo {
        private int type;

        public UpdateVideo(int type) {
            this.type = type;
        }

        public UpdateVideo() {
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
