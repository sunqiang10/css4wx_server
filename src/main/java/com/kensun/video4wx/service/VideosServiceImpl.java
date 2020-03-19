package com.kensun.video4wx.service;
import com.kensun.video4wx.dao.CourseDao;
import com.kensun.video4wx.dao.VideosDao;
import com.kensun.video4wx.model.VideoCourse;
import com.kensun.video4wx.model.Videos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Service
public class VideosServiceImpl implements VideosService {
    @Autowired
    private VideosDao dao;
    @Autowired
    private CourseDao courseDao;
    @Override
    public List<Videos> findByVideoCourse(final VideoCourse course) {
        Specification<Videos> specification=new Specification<Videos>() {
            /*
             * @param root:代表的查询的实体类
             * @param query：可以从中得到Root对象，即告知JPA Criteria查询要查询哪一个实体类，
             * 还可以来添加查询条件，还可以结合EntityManager对象得到最终查询的TypedQuery 对象
             * @Param cb:criteriabuildre对象，用于创建Criteria相关的对象工程，当然可以从中获取到predicate类型
             * @return:代表一个查询条件
             */
            @Override
            public Predicate toPredicate(Root<Videos> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {

                Predicate p = cb.equal(root.get("videoCourse"),course);
                return p;
            }
        };
        List<Videos> list = dao.findAll(specification);

        return list;
    }

    @Override
    public List<Videos> findAll() {
        return dao.findAll();
    }

    @Override
    public Videos findOne(Long id) {
        return dao.findOne(id);
    }
}