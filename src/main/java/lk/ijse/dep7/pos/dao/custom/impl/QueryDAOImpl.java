package lk.ijse.dep7.pos.dao.custom.impl;

import lk.ijse.dep7.pos.dao.custom.QueryDAO;
import lk.ijse.dep7.pos.entity.CustomEntity;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Scope("prototype")
@Component
public class QueryDAOImpl implements QueryDAO {

    private EntityManager em;

    @Override
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<HashMap<String, Object>> findOrders(String query) {

        List<HashMap<String, Object>> orderList = new ArrayList<>();

        String[] searchWords = query.split("\\s");
        StringBuilder sqlBuilder = new StringBuilder("SELECT o.*, c.name, order_total.total\n" +
                "FROM `order` o\n" +
                "         INNER JOIN customer c on o.customer_id = c.id\n" +
                "         INNER JOIN\n" +
                "     (SELECT order_id, SUM(qty * unit_price) AS total FROM order_detail od GROUP BY order_id) AS order_total\n" +
                "     ON o.id = order_total.order_id\n" +
                "WHERE (order_id LIKE ?\n" +
                "    OR date LIKE ?\n" +
                "    OR customer_id LIKE ?\n" +
                "    OR name LIKE ?) ");

        for (int i = 1; i < searchWords.length; i++) {
            sqlBuilder.append("AND (\n" +
                    "            order_id LIKE ?\n" +
                    "        OR date LIKE ?\n" +
                    "        OR customer_id LIKE ?\n" +
                    "        OR name LIKE ?)");
        }

        Query nativeQuery = em.createNativeQuery(sqlBuilder.toString());

        for (int i = 0; i < searchWords.length * 4; i++) {
            nativeQuery.setParameter(i + 1, "%" + searchWords[(i / 4)] + "%");
        }

        List<Object[]> list = nativeQuery.getResultList();

        for (Object[] record : list) {
            HashMap<String, Object> newRecord = new HashMap<>();
            newRecord.put("id", record[0]);
            newRecord.put("date", record[1]);
            newRecord.put("customer_id", record[2]);
            newRecord.put("name", record[3]);
            newRecord.put("total", record[4]);
        }

        return orderList;

    }

    @Override
    public long countOrders(String query) {
        String[] searchWords = query.split("\\s");
        StringBuilder sqlBuilder = new StringBuilder("SELECT COUNT(*) \n" +
                "FROM `order` o\n" +
                "         INNER JOIN customer c on o.customer_id = c.id\n" +
                "         INNER JOIN\n" +
                "     (SELECT order_id, SUM(qty * unit_price) AS total FROM order_detail od GROUP BY order_id) AS order_total\n" +
                "     ON o.id = order_total.order_id\n" +
                "WHERE (order_id LIKE ?\n" +
                "    OR date LIKE ?\n" +
                "    OR customer_id LIKE ?\n" +
                "    OR name LIKE ?) ");

        for (int i = 1; i < searchWords.length; i++) {
            sqlBuilder.append("AND (\n" +
                    "            order_id LIKE ?\n" +
                    "        OR date LIKE ?\n" +
                    "        OR customer_id LIKE ?\n" +
                    "        OR name LIKE ?)");
        }

        Query nativeQuery = em.createNativeQuery(sqlBuilder.toString());

        for (int i = 0; i < searchWords.length * 4; i++) {
            nativeQuery.setParameter(i + 1, "%" + searchWords[(i / 4)] + "%");
        }

        return (long) nativeQuery.getSingleResult();
    }

    @Override
    public List<CustomEntity> findOrders(String query, int page, int size)  {

        String[] searchWords = query.split("\\s");
        StringBuilder sqlBuilder = new StringBuilder("SELECT o.id as orderId, o.date as orderDate, o.customer_id as customerId, c.name as customerName, order_total.total as orderTotal \n" +
                "FROM `order` o\n" +
                "         INNER JOIN customer c on o.customer_id = c.id\n" +
                "         INNER JOIN\n" +
                "     (SELECT order_id, SUM(qty * unit_price) AS total FROM order_detail od GROUP BY order_id) AS order_total\n" +
                "     ON o.id = order_total.order_id\n" +
                "WHERE (order_id LIKE ?\n" +
                "    OR date LIKE ?\n" +
                "    OR customer_id LIKE ?\n" +
                "    OR name LIKE ?) ");

        for (int i = 1; i < searchWords.length; i++) {
            sqlBuilder.append("AND (\n" +
                    "            order_id LIKE ?\n" +
                    "        OR date LIKE ?\n" +
                    "        OR customer_id LIKE ?\n" +
                    "        OR name LIKE ?)");
        }
        sqlBuilder.append(" LIMIT ? OFFSET ?");

        Query nativeQuery = em.createNativeQuery(sqlBuilder.toString());

        for (int i = 0; i < searchWords.length * 4; i++) {
            nativeQuery.setParameter(i + 1, "%" + searchWords[(i / 4)] + "%");
        }
        nativeQuery.setParameter((searchWords.length * 4) + 1, size);
        nativeQuery.setParameter((searchWords.length * 4) + 2, size * (page - 1));

        List<Object[]> resultList = nativeQuery.getResultList();
        List<CustomEntity> orders = new ArrayList<>();

        for (Object[] row : resultList) {
            orders.add(new CustomEntity((String) row[0],
                    (Date) row[1],
                    (String) row[2],
                    (String) row[3],
                    (BigDecimal) row[4]));
        }
        return orders;
    }

}
