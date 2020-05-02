package ru.omsu.imit.mappers;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import ru.omsu.imit.model.Address;
import ru.omsu.imit.model.Contact;

public interface AddressMapper {

    @Insert("INSERT INTO address ( contactid, postcode, country, city, street, house, block, flat)" +
            " VALUES ( #{contact.id}," +
            " #{address.postcode}," +
            " #{address.country}," +
            " #{address.city}," +
            " #{address.street}," +
            " #{address.house}," +
            " #{address.block}," +
            " #{address.flat})")
    void insert(@Param("address") Address address, @Param("contact") Contact contact);

    @Select("SELECT contactid, postcode, country, city, street, house, block, flat " +
            "FROM address WHERE contactid = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "contact", column = "id", javaType = Contact.class,
                    one = @One(select = "ru.omsu.imit.mappers.ContactMapper.getById",
                            fetchType = FetchType.LAZY))
    })
    Address getByContactId(@Param("id") int id);

    @Update("UPDATE address SET postcode = #{address.postcode}," +
            " country = #{address.country}," +
            " city = #{address.city}," +
            " street = #{address.street}," +
            " house = #{address.house}," +
            " block = #{address.block}," +
            " flat = #{address.flat}" +
            " WHERE `contactid` = #{address.contact.id}")
    void update(@Param("address") Address address);
}

