package com.example.infra.common.entity;

import java.util.List;

/**
 * Created by zou on 2016/5/25.
 */
public interface IListEntity<T extends List> {

    T getList();
}
