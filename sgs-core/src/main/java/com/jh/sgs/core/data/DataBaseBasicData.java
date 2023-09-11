package com.jh.sgs.core.data;

import com.jh.sgs.core.interfaces.BasicData;
import com.jh.sgs.core.pojo.Card;
import com.jh.sgs.core.pojo.General;
import com.jh.sgs.core.pojo.Skill;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.*;

/**
 * 数据库基本数据获取
 */
@Log4j2
public class DataBaseBasicData implements BasicData {


    private String url;
    private String username;
    private String password;
    private Connection connection;
    private Statement statement;


    public DataBaseBasicData(String propFilePath) {
        Properties properties = new Properties();
        try {
            if (propFilePath.contains("classpath:")) {
                properties.load(getClass().getClassLoader().getResourceAsStream(propFilePath.substring(10)));

            } else {
                properties.load(Files.newInputStream(Paths.get(propFilePath)));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        username = properties.getProperty("username");
        url = properties.getProperty("url");
        password = properties.getProperty("password");
        initDataBase();
    }


    public DataBaseBasicData(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
        initDataBase();
    }

    private void initDataBase() {
        try {
            connection = DriverManager.getConnection(url, username, password);
            statement = connection.createStatement();
            log.info("数据库连接成功");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void closeDataBase() {
        try {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
        }
    }

    @Override
    public List<Card> getCards() {
        try (ResultSet resultSet = statement.executeQuery("select * from card  where name_id in(14,15,9,1)")) {
            ArrayList<Card> cards = new ArrayList<>();
            while (resultSet.next()) {
                Card card = new Card();
                card.setId(resultSet.getInt("id"));
                card.setNum(resultSet.getString("num"));
//                card.setName(resultSet.getString("name"));
//                card.setRemark(resultSet.getString("remark"));
                card.setSuit(resultSet.getInt("suit"));
                card.setNameId(resultSet.getInt("name_id"));
                cards.add(card);
            }
            return cards;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<Integer, Map<String, String>> getCardParameter() {
        try (ResultSet resultSet = statement.executeQuery("select * from card_parameter  ")) {
            Map<Integer, Map<String, String>> map = new HashMap<>();
            while (resultSet.next()) {
                HashMap<String, String> stringStringHashMap = new HashMap<>();
                stringStringHashMap.put("name", resultSet.getString("name"));
                stringStringHashMap.put("remark", resultSet.getString("remark"));
                map.put(resultSet.getInt("id"), stringStringHashMap);
            }
            return map;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<String, Integer> getIdentity(int num) {
        try (ResultSet resultSet = statement.executeQuery("select * from identity where num=" + num + " limit 1")) {
            if (resultSet.next()) {
                HashMap<String, Integer> identity = new HashMap<>();
                identity.put("zhu", resultSet.getInt("zhu"));
                identity.put("zhong", resultSet.getInt("zhong"));
                identity.put("fan", resultSet.getInt("fan"));
                identity.put("nei", resultSet.getInt("nei"));
                return identity;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<General> getGenerals() {
        try (ResultSet resultSet = statement.executeQuery("select * from general")) {
            ArrayList<General> generals = new ArrayList<>();
            while (resultSet.next()) {
                General general = new General();
                general.setId(resultSet.getInt("id"));
                general.setName(resultSet.getString("name"));
                general.setCountry(resultSet.getString("country"));
                general.setBlood(resultSet.getInt("blood"));
                String[] skillIds = resultSet.getString("skill_ids").split(",");
                general.setSkillIds(Arrays.stream(skillIds).mapToInt(Integer::parseInt).toArray());
                general.setSkills(Arrays.stream(skillIds).map(s -> {
                    Skill skill = new Skill();
                    skill.setId(Integer.parseInt(s));
                    return skill;
                }).toArray(value -> new Skill[skillIds.length]));
                generals.add(general);
            }
            return generals;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
