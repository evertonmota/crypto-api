package com.dev.crypto.repository;

import com.dev.crypto.dto.CoinTransactionDTO;
import com.dev.crypto.entity.Coin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Transient;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Repository
@EnableAutoConfiguration
public class CoinRepository {
    @Autowired
    private EntityManager entityManager;
    public List<CoinTransactionDTO> getAll(){
        String jpql = " select new com.dev.crypto.dto.CoinTransactionDTO(c.name, sum(c.quantity)) from coin c group by c.name";
        TypedQuery<CoinTransactionDTO> query = entityManager.createQuery(jpql, CoinTransactionDTO.class);
        return query.getResultList()   ;
    }

    public List<Coin> getByName(String name){
        String jpql = " select c from coin c where c.name like :name ";
        TypedQuery<Coin> query = entityManager.createQuery(jpql, Coin.class);
        query.setParameter("name", "%" + name + "%");
        return query.getResultList();
    }

    @Transactional
    public Coin insert(Coin coin) {
        entityManager.persist(coin);
        return coin;
    }
    @Transactional
    public Coin update(Coin coin) {
        entityManager.merge(coin);
        return coin;
    }
    @Transactional
    public Boolean remove(int id) throws Exception {
        Coin coin = entityManager.find(Coin.class, id);
        if(coin == null)
            throw new RuntimeException();

        entityManager.remove(coin);
        return true;
    }



    /* Utiilizando JDBC
    private static String INSERT = "insert into coin (name, price, quantity, datetime) values (?,?,?,?)";
    private static String SELECT_ALL = "select name, sum( quantity ) as quantity from coin group by name";
    private static String DELETE= "delete from coin where id = ?";

    private static String UPDATE ="update coin set name = ? , price = ? , quantity = ? where id = ? ";
    private static String SELECT_BY_NAME = "select * from coin where name = ?";
    private JdbcTemplate jdbcTemplate;

    public CoinRepository(JdbcTemplate template) {
        this.jdbcTemplate = template;
    }

    public Coin insert(Coin coin) {

        Object[] attr = new Object[]{
                coin.getName(),
                coin.getPrice(),
                coin.getQuantity(),
                coin.getDateTime()
        };
        jdbcTemplate.update(INSERT, attr);
        return coin;
    }

    public Coin update (Coin object){
        Object[] attr = new Object[]{
           object.getName(),
           object.getPrice(),
           object.getQuantity(),
           object.getId()
        };
        jdbcTemplate.update(UPDATE, attr);
        return object;
    }
    public List<CoinTransactionDTO> getAll() {
       return   jdbcTemplate.query(SELECT_ALL, new RowMapper<CoinTransactionDTO>() {
            @Override
            public CoinTransactionDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

                CoinTransactionDTO coin = new CoinTransactionDTO();
                coin.setName(rs.getString("name"));
                coin.setQuantity(rs.getBigDecimal("quantity"));
                return coin;
            }
        });
    }

    public List<Coin> getByName(String name){

        Object[] attr = new Object[]{name };
        return jdbcTemplate.query(SELECT_BY_NAME, new RowMapper<Coin>() {
            @Override
            public Coin mapRow(ResultSet rs, int rowNum) throws SQLException {
                Coin coin = new Coin();

                coin.setId(rs.getInt("id"));
                coin.setName(rs.getString("name"));
                coin.setPrice(rs.getBigDecimal("price"));
                coin.setQuantity(rs.getBigDecimal("quantity"));
                coin.setDateTime(rs.getTimestamp("dateTime"));

                return coin;
            }
        }, attr);
    }

    public int remove(int id){
        return jdbcTemplate.update(DELETE, id);
    }
    */

}
