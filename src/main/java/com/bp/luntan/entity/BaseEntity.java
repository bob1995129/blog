package com.bp.luntan.entity;
import lombok.Data;
import java.util.Date;

/**
 * @author b-p
 * @version 1.0
 * @date 2020/7/3 11:47
 */
@Data
public class BaseEntity {

    private Long id;
    private Date created;
    private Date modified;
}
