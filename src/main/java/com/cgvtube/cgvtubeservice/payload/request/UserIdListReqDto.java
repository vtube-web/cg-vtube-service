package com.cgvtube.cgvtubeservice.payload.request;

import lombok.Data;

import java.util.List;

@Data

public class UserIdListReqDto {
    List<Long> userIdList;
}
