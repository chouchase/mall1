package com.chouchase.dao;

import com.chouchase.domain.User;
import org.apache.ibatis.annotations.Param;

public interface UserDao {
    public int countUsername(String username);

    public int countEmail(String Email);

    public int countPhone(String Phone);

    public int countEmailByOtherId(@Param("email") String Email, @Param("id") Integer id);

    public int countPhoneByOtherId(@Param("phone") String Phone, @Param("id") Integer id);

    public int insertUser(User user);

    public User selectUserByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    public String selectQuestionByUsername(String username);

    public User selectUserByPrimaryKey(Integer id);

    public int countUserQuestionAnswer(@Param("username") String username, @Param("question") String question, @Param("answer") String answer);

    public int updatePasswordByUsername(@Param("username") String username, @Param("password") String password);

    public int countPasswordByUserId(@Param("id") Integer id, @Param("password") String password);

    public int updatePasswordByUserId(@Param("id") Integer id, @Param("password") String password);

    public int updateUserBySelective(User user);
}
