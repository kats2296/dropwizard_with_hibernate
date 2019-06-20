package com.cleartax.docmanagement.dao;

import com.cleartax.docmanagement.domain.Partner;
import com.cleartax.docmanagement.mapper.PartnerMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

@RegisterMapper(PartnerMapper.class)
public interface PartnerDao {

    @SqlQuery("select * from partner;")
    List<Partner> getAllPartners();

    @SqlQuery("select * from partner where partnerId = :partnerId;")
    Partner getPartnerById(@Bind("partnerId") final int partnerId);

    @SqlQuery("select * from partner where userId = :userId;")
    List<Partner> getPartnersByUserId(@Bind("userId") final int userId);

    @SqlUpdate("insert into partner values (:partnerId,:userId, :partnerName, :partnerEmail);")
    void createPartner(@BindBean final Partner partner);

    @SqlUpdate("update partner set partnerId = coalesce(:partnerId, partnerId), userId = coalesce(:userId, userId), " +
            " partnerName = coalesce(:partnerName, partnerName), partnerEmail = coalesce(:partnerEmail, partnerEmail)" +
            " where partnerId = :partnerId")
    void updatePartner(@BindBean final Partner partner);

    @SqlUpdate("delete from partner where partnerId = :partnerId")
    int deletePartner(@Bind("partnerId") final int partnerId);

    @SqlQuery("select last_insert_id();")
    int lastInsertId();

    @SqlQuery("select partnerId from partner where userId = :userId")
    List<Integer> getPartnerIdsByUserId(@Bind("userId") final int userId);
}
