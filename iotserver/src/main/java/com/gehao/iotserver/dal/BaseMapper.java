package com.gehao.iotserver.dal;

/**
 * @author gehao
 */
public interface BaseMapper<T> {
    /**
     * 通过id获取记录
     *
     * @param id
     * @return
     */
    T getById(Long id);

    /**
     * 插入
     *
     * @param t
     * @return
     */
    int insert(T t);

    /**
     * 修改
     *
     * @param t
     * @return
     */
    int update(T t);

    /**
     * 删除
     *
     * @param t
     * @return
     */
    int delete(T t);
}
