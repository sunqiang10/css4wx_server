package com.kensun.video4wx.service;
import com.kensun.video4wx.dao.UserDao;
import com.kensun.video4wx.model.Users;
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
public class UserServiceImpl implements UserService {    @Autowired
    private UserDao dao;
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryUsernameIsExist(final String userName) {
        Specification<Users> specification=new Specification<Users>() {
            /*
             * @param root:代表的查询的实体类
             * @param query：可以从中得到Root对象，即告知JPA Criteria查询要查询哪一个实体类，
             * 还可以来添加查询条件，还可以结合EntityManager对象得到最终查询的TypedQuery 对象
             * @Param cb:criteriabuildre对象，用于创建Criteria相关的对象工程，当然可以从中获取到predicate类型
             * @return:代表一个查询条件
             */
            @Override
            public Predicate toPredicate(Root<Users> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate p = cb.equal(root.get("userName"),userName);
                return p;
            }
        };
        List<Users> list = dao.findAll(specification);
        if(list.size()>0){
            return true;
        }else{
            return false;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveUser(Users user) {
        dao.save(user);
    }

    @Override
    public Users queryByUserName(final String userName) {
        Specification<Users> specification=new Specification<Users>() {
            /*
             * @param root:代表的查询的实体类
             * @param query：可以从中得到Root对象，即告知JPA Criteria查询要查询哪一个实体类，
             * 还可以来添加查询条件，还可以结合EntityManager对象得到最终查询的TypedQuery 对象
             * @Param cb:criteriabuildre对象，用于创建Criteria相关的对象工程，当然可以从中获取到predicate类型
             * @return:代表一个查询条件
             */
            @Override
            public Predicate toPredicate(Root<Users> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate p = cb.equal(root.get("userName"),userName);
                return p;
            }
        };
        List<Users> list = dao.findAll(specification);
        if(list.size()>0){
            return list.get(0);
        }else{
            return null;
        }
    }

    @Override
    public Users queryByUserId(final String userId) {
        Specification<Users> specification=new Specification<Users>() {
            /*
             * @param root:代表的查询的实体类
             * @param query：可以从中得到Root对象，即告知JPA Criteria查询要查询哪一个实体类，
             * 还可以来添加查询条件，还可以结合EntityManager对象得到最终查询的TypedQuery 对象
             * @Param cb:criteriabuildre对象，用于创建Criteria相关的对象工程，当然可以从中获取到predicate类型
             * @return:代表一个查询条件
             */
            @Override
            public Predicate toPredicate(Root<Users> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate p = cb.equal(root.get("userId"),userId);
                return p;
            }
        };
        List<Users> list = dao.findAll(specification);
        if(list.size()>0){
            return list.get(0);
        }else{
            return null;
        }
    }

}