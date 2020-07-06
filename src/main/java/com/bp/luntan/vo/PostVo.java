package com.bp.luntan.vo;

import com.bp.luntan.entity.Post;
import lombok.Data;

/**
 * @author b-p
 * @version 1.0
 * @date 2020/7/3 14:32
 */
@Data
public class PostVo extends Post {

    private Long authorId;
    private String authorName;
    private String authorAvatar;

    private String categoryName;

}
