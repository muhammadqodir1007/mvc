package com.epam.dao.repo.tagRepo;

public interface TagQueries {


    String getAll = "select * from public.\"tags\" ";
    String getOne = "select * from public.\"tags\"  where id=?";
    String getByName = "select * from public.\"tags\"  where tag_name=?";
    String getById = "select * from public.\"tags\"  where id=?";

    String existByName = "select count (*) from public.\"tags\"  where tag_name=?";
    String update = "update public.\"tags\" set tag_name=? where id=?";
    String insert = "INSERT INTO public.tags (tag_name) VALUES (?)";
    String delete = "delete from public.\"tags\"  where id=?";
    String existInGcT="select count(*) from gift_certificates_tags where tag_id=?";
    String deleteFromGct="delete from gift_certificates_tags where tag_id=?";



}
