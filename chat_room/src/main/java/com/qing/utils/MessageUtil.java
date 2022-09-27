package com.qing.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qing.pojo.ResultMessage;

/**
 * @author lianggq
 * @date 2022/9/22 15:33
 */
public class MessageUtil {

    public static String getMessage(boolean isSystem, String fromName, Object message) {
        try {
            ResultMessage resultMessage = new ResultMessage();
            resultMessage.setSystem(isSystem);
            if (fromName != null) {
                resultMessage.setFromName(fromName);
            }
            resultMessage.setMessage(message);
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(resultMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
