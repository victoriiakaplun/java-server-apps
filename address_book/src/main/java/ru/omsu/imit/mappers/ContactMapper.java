package ru.omsu.imit.mappers;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import ru.omsu.imit.model.Address;
import ru.omsu.imit.model.Contact;

import java.util.List;


public interface ContactMapper {

        @Insert("INSERT INTO contact ( firstname, lastname, patronymic, birthdate, phone, email)" +
                " VALUES ( #{firstName}," +
                " #{lastName}," +
                " #{patronymic}," +
                " #{birthdate}," +
                " #{phone}," +
                " #{email})")
        @Options(useGeneratedKeys = true, keyProperty = "id")
        Integer insert(Contact contact);

        @Update("UPDATE contact SET firstname = #{contact.firstName}," +
                " lastname = #{contact.lastName}," +
                " patronymic = #{contact.patronymic}," +
                " birthdate = #{contact.birthdate}," +
                " phone = #{contact.phone}," +
                " email = #{contact.email}" +
                " WHERE id = #{contact.id}")
        void update(@Param("contact") Contact contact);

       @Select("SELECT id, firstname as firstName, lastname as lastName, patronymic,birthdate, phone, email " +
                "FROM contact WHERE id = #{id}")
       @Results({
                @Result(property = "id", column = "id"),
                @Result(property = "address", column = "id", javaType = Address.class,
                        one = @One(select = "ru.omsu.imit.mappers.AddressMapper.getByContactId",
                                fetchType = FetchType.LAZY)),
                @Result(property = "sections", column = "id", javaType = List.class,
                        many = @Many(select = "ru.omsu.imit.mappers.ContactSectionMapper.getContactSections",
                               fetchType = FetchType.LAZY))})
       Contact getById(@Param("id") int id);

        @Delete("DELETE FROM contact WHERE id = #{id}")
        void delete(@Param("id") int id);

        @Delete("DELETE FROM contact")
        void deleteAll();

        @Select("SELECT id, firstname as firstName, lastname as lastName, patronymic, birthdate, phone, email FROM contact" )
        @Results({
                @Result(property = "id", column = "id"),
                @Result(property = "address", column = "id", javaType = Address.class,
                        one = @One(select = "ru.omsu.imit.mappers.AddressMapper.getByContactId",
                                fetchType = FetchType.LAZY)),
                @Result(property = "sections", column = "id", javaType = List.class,
                        many = @Many(select = "ru.omsu.imit.mappers.ContactSectionMapper.getContactSections",
                                fetchType = FetchType.LAZY))})
        List<Contact> getAll();
}
