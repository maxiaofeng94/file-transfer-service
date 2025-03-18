package com.feng.filetransfers.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 文件存在状态
 */
@Getter
public enum FileStatus {
    EXIST(1, "存在"),
    NONE(0, "不存在");

    /**
     * 文件状态值
     */
    @JsonValue
    private final int value;

    /**
     * 文件状态说明
     */
    private final String message;

    FileStatus(int value, String message) {
        this.value = value;
        this.message = message;
    }
}
