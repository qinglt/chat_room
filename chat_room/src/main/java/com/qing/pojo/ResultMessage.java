package com.qing.pojo;

import lombok.Data;

/**
 * @author lianggq
 * @date 2022/9/22 15:29
 */
@Data
public class ResultMessage {

    private boolean isSystem;

    private String fromName;

    // 消息可能是数组
    private Object message;

}
