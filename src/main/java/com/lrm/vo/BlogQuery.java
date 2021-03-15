package com.lrm.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by limi on 2017/10/20.
 */

@Data
@NoArgsConstructor
public class BlogQuery {

    private String title;
    private Long typeId;
    private boolean recommend;

}
