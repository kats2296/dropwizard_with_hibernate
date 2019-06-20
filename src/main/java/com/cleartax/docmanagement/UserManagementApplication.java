package com.cleartax.docmanagement;

import com.cleartax.docmanagement.config.UserManagementConfiguration;
import com.cleartax.docmanagement.controller.*;
import com.cleartax.docmanagement.dao.*;
import com.cleartax.docmanagement.domain.*;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class UserManagementApplication extends Application<UserManagementConfiguration> {

    private static final String SQL = "sql";

    private final HibernateBundle<UserManagementConfiguration> hibernateBundle
            = new HibernateBundle<UserManagementConfiguration>(User.class, Document.class, SharedLink.class,
            Partner.class, PartnerStatus.class)
    {

        @Override
        public DataSourceFactory getDataSourceFactory(
                UserManagementConfiguration configuration
        ) {
            return configuration.getDataSourceFactory();
        }

    };

    @Override
    public void initialize(final Bootstrap<UserManagementConfiguration> bootstrap) {
        bootstrap.addBundle(hibernateBundle);
    }


    @Override
    public void run(UserManagementConfiguration userManagementConfiguration, Environment environment) throws Exception {
       final UserDAO userDAO = new UserDAO(hibernateBundle.getSessionFactory());
       final DocumentDAO documentDAO = new DocumentDAO(hibernateBundle.getSessionFactory());
       final SharedLinkDAO sharedLinkDAO = new SharedLinkDAO(hibernateBundle.getSessionFactory());
       final PartnerDAO partnerDAO = new PartnerDAO(hibernateBundle.getSessionFactory());
       final PartnerStatusDAO partnerStatusDAO = new PartnerStatusDAO(hibernateBundle.getSessionFactory());

       environment.jersey().register(new UserRestController(userDAO));
       environment.jersey().register(new DocumentRestController(documentDAO, userDAO));
       environment.jersey().register(new SharedLinkController(sharedLinkDAO, partnerDAO, documentDAO, partnerStatusDAO, userDAO));
       environment.jersey().register(new PartnerRestController(partnerDAO));
       environment.jersey().register(new PartnerStatusController(partnerStatusDAO, documentDAO, partnerDAO));

    }

    public static void main(String[] args) throws Exception {
        new UserManagementApplication().run(args);
    }
}
