package ru.omsu.imit.mappers;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import ru.omsu.imit.model.Address;
import ru.omsu.imit.model.Contact;
import ru.omsu.imit.model.Section;

import java.util.List;

public interface ContactSectionMapper {
    @Insert("INSERT into contact_section (contactid, sectionid)" +
    "VALUES (#{contact.id}, #{section.id})")
    void addContactToSection(@Param("contact") Contact contact, @Param("section") Section section);

    @Delete("DELETE from contact_section WHERE contactid = #{contact.id} AND sectionid = #{section.id}")
    void removeContactFromSection(@Param("contact") Contact contact, @Param("section") Section section);

    @Select("SELECT id, title from section WHERE id IN(SELECT sectionid FROM contact_section WHERE contactid = #{contact.id})")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "sectionContacts", column = "id", javaType = List.class,
                    many = @Many(select = "ru.omsu.imit.mappers.ContactSectionMapper.getSectionContacts",
                            fetchType = FetchType.LAZY))})
    List<Section> getContactSections(@Param("contact") Contact contact);

    @Select("SELECT id, firstname as firstName, lastname as lastName, patronymic,birthdate, phone, email from contact " +
            "WHERE id IN(SELECT contactid FROM contact_section WHERE sectionid = #{section.id})")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "address", column = "id", javaType = Address.class,
                    one = @One(select = "ru.omsu.imit.mappers.AddressMapper.getByContactId",
                            fetchType = FetchType.LAZY)),
            @Result(property = "sections", column = "id", javaType = List.class,
                    many = @Many(select = "ru.omsu.imit.mappers.ContactSectionMapper.getContactSections",
                            fetchType = FetchType.LAZY))})
    List<Contact> getSectionContacts(@Param("section") Section section);
}
