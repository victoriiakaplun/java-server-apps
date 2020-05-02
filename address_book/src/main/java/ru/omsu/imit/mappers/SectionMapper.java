package ru.omsu.imit.mappers;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import ru.omsu.imit.model.Section;

import java.util.List;

public interface SectionMapper {

    @Insert("INSERT into section (title) VALUES (#{section.title})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer insert(@Param("section") Section section);

    @Select("SELECT id, title from section WHERE id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "sectionContacts", column = "id", javaType = List.class,
                    many = @Many(select = "ru.omsu.imit.mappers.ContactSectionMapper.getSectionContacts",
                    fetchType = FetchType.LAZY))})
    Section getById(@Param("id") int id);

    @Delete("DELETE from section WHERE id = #{id}")
    void delete(@Param("id") int id);

    @Select("SELECT id, title from section")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "sectionContacts", column = "id", javaType = List.class,
                    many = @Many(select = "ru.omsu.imit.mappers.ContactSectionMapper.getSectionContacts",
                            fetchType = FetchType.LAZY))})
    List<Section> getAll();

    @Delete("DELETE FROM section")
    void deleteAll();

    @Update("UPDATE section SET title = #{section.title}" +
            " WHERE id = #{section.id}")
    void update(@Param("section") Section section);
}
