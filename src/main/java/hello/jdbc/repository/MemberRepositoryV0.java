package hello.jdbc.repository;

import hello.jdbc.connection.DBConnectionUtil;
import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.NoSuchElementException;

@Slf4j
public class MemberRepositoryV0 {
    public Member save(Member member) throws SQLException {
        String sql = "insert into member(member_id, money) values (?, ?)";
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, member.getMemberId());
            pstmt.setInt(2, member.getMoney());
            int i = pstmt.executeUpdate();
            log.info("executeUpdate done : " + i);
            return member;
        } catch (SQLException e) {
            log.info("db error", e);
            throw e;
        } finally {
            close(con, pstmt, null);
        }
    }

    public Member findById(String memberId) throws SQLException {
        String sql = "select * from member where member_id = ?";
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            con = getConnection();
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, memberId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Member member = new Member();
                member.setMemberId(resultSet.getString("member_id"));
                member.setMoney(resultSet.getInt("money"));
                return member;
            } else {
                throw new NoSuchElementException("member not found memberId = " + memberId);
            }
        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        } finally {
            close(con, preparedStatement, resultSet);
        }
    }

    /**
     * stmt.close()에서 Exception이 발생될 경우에 대비하여 각각의 close문에 try-catch문을 감싸서 처리.
     * */
    private void close(Connection con, Statement stmt, ResultSet re){
        if (re != null) {
            try {
                re.close();
            }catch (SQLException e){
                log.info("error", e);
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            }catch (SQLException e){
                log.info("error", e);
            }
        }
        if (con != null) {
            try {
                con.close();
            }catch (SQLException e){
                log.info("error", e);
            }
        }

    }
    private Connection getConnection() {
        return DBConnectionUtil.getConnection();
    }

}
