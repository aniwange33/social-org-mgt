---organization
CREATE TABLE Organization (
                              ID BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                              name VARCHAR(255) NOT NULL,
                              establishment_date DATE NOT NULL
);

--member
CREATE TABLE Member (
                        ID BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                        last_name VARCHAR(255) NOT NULL,
                        first_name VARCHAR(255) NOT NULL,
                        nick_name VARCHAR(255),
                        address VARCHAR(255),
                        id_number VARCHAR(255) UNIQUE NOT NULL,
                        current_town VARCHAR(255),
                        home_town VARCHAR(255),
                        date_of_birth DATE NOT NULL,
                        created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        updated_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        organization BIGINT,
                        foreign key (organization) references Organization(id)
);
