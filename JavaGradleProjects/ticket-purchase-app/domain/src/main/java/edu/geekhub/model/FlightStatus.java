package edu.geekhub.model;

public enum FlightStatus {

    SCHEDULED {
        @Override
        public boolean canChangeTo(FlightStatus toStatus) {
            return toStatus == CANCELLED
                || toStatus == BOARDING;
        }
    },

    CANCELLED {
        @Override
        public boolean canChangeTo(FlightStatus toStatus) {
            return false;
        }
    },

    BOARDING {
        @Override
        public boolean canChangeTo(FlightStatus toStatus) {
            return toStatus == CANCELLED
                || toStatus == DEPARTED;
        }
    },

    DEPARTED {
        @Override
        public boolean canChangeTo(FlightStatus toStatus) {
            return toStatus == LANDED;
        }
    },

    LANDED {
        @Override
        public boolean canChangeTo(FlightStatus toStatus) {
            return toStatus == ARRIVED;
        }
    },

    ARRIVED {
        @Override
        public boolean canChangeTo(FlightStatus toStatus) {
            return false;
        }
    };

    public abstract boolean canChangeTo(FlightStatus toStatus);
}
