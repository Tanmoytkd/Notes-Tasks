package net.therap.notestasks.domain;

import java.io.Serializable;

/**
 * @author tanmoy.das
 * @since 5/5/20
 */
public enum AccessLevel implements Serializable {
    READ,
    WRITE,
    SHARE,
    DELETE
}
