package dk.nielshvid.intermediator;

import java.time.LocalDateTime;

public interface Oracles {
    LocalDateTime getAccessed(String ID);
}
