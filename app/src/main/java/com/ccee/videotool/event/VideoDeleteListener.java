package com.ccee.videotool.event;

public interface VideoDeleteListener {
    void onDelete(DeleteVideo o);

    class DeleteVideo{
        private int id;

        public DeleteVideo(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
