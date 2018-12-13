package com.zgczx.repository.tea;

import com.zgczx.dataobject.StuPrepareSub;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Dqd on 2018/12/12.
 */
public interface StuPrepareSubRepository extends JpaRepository<StuPrepareSub,String> {
    List<StuPrepareSub> findByCourserId(Integer courserId, Pageable pageable);
    List<StuPrepareSub> deleteByCourserId(Integer courserId);
}
