package com.epam.dao.repo.tagRepo;

public class TagQueries {


   public static final String GET_ALL = "select * from public.\"tags\" ";
    public static final String GET_BY_NAME = "select * from public.\"tags\"  where tag_name=?";
    public static final String GET_BY_ID = "select * from public.\"tags\"  where id=?";

    public static final String EXIST_BY_NAME = "select count (*) from public.\"tags\"  where tag_name=?";
    public static final String INSERT = "INSERT INTO public.tags (tag_name) VALUES (?)";
    public static final String DELETE = "delete from public.\"tags\"  where id=?";
    public static final String EXIST_IN_GCT = "select count(*) from gift_certificates_tags where tag_id=?";
    public static final String DELETE_FROM_GCT = "delete from gift_certificates_tags where tag_id=?";


}
