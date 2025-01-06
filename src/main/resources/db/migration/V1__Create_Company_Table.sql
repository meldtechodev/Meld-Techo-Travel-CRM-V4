CREATE TABLE company_master (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    company_name VARCHAR(100) NOT NULL,
    parent_id BIGINT NULL,
    company_address VARCHAR(255) NOT NULL,
    company_email VARCHAR(150),
    company_country_code VARCHAR(20),
    company_phone VARCHAR(15),
    company_website VARCHAR(255),
    company_logo_path JSON  NULL,
    ipaddress VARCHAR(50),
    status BOOLEAN DEFAULT TRUE,
    isdelete BOOLEAN DEFAULT FALSE,
    created_by VARCHAR(50),
    modified_by VARCHAR(50),
    created_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
CREATE TABLE Roles_Master (
    id BIGINT AUTO_INCREMENT PRIMARY KEY, -- Auto-incrementing primary key
    role_name VARCHAR(255) UNIQUE NOT NULL, -- Unique role name
    ipaddress VARCHAR(255), -- IP address
    status BOOLEAN NOT NULL DEFAULT TRUE, -- Status column, default true
    isdelete BOOLEAN NOT NULL DEFAULT FALSE, -- Is deleted flag, default false
    created_by VARCHAR(255), -- Creator information
    modified_by VARCHAR(255), -- Modifier information
    created_date DATETIME DEFAULT CURRENT_TIMESTAMP, -- Auto-set on creation
    modified_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP -- Auto-set on update
);
CREATE TABLE Departments_master (
    id BIGINT AUTO_INCREMENT PRIMARY KEY, -- Auto-incrementing primary key
    department_name VARCHAR(255) NOT NULL, -- Department name (validated in application layer)
    created_by VARCHAR(255), -- Created by user
    modified_by VARCHAR(255), -- Modified by user
    ipaddress VARCHAR(255), -- IP address
    status BOOLEAN NOT NULL DEFAULT TRUE, -- Status column, default true
    isdelete BOOLEAN NOT NULL DEFAULT FALSE, -- Is deleted flag, default false
    created_date DATETIME DEFAULT CURRENT_TIMESTAMP, -- Auto-set creation date
    modified_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP -- Auto-set modification date
);

CREATE TABLE designations_master (
    id BIGINT AUTO_INCREMENT PRIMARY KEY, -- Auto-incrementing primary key
    designation_name VARCHAR(255) NOT NULL, -- Designation name (validated in application layer)
    created_by VARCHAR(255), -- Created by user
    modified_by VARCHAR(255), -- Modified by user
    ipaddress VARCHAR(255), -- IP address
    department_id BIGINT, -- Foreign key to Departments
    status BOOLEAN NOT NULL DEFAULT TRUE, -- Status column, default true
    isdelete BOOLEAN NOT NULL DEFAULT FALSE, -- Is deleted flag, default false
    created_date DATETIME DEFAULT CURRENT_TIMESTAMP, -- Auto-set creation date
    modified_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- Auto-set modification date
     FOREIGN KEY (department_id) REFERENCES Departments_master (id) ON DELETE SET NULL -- Foreign key constraint
);
CREATE TABLE modules_master (
    id BIGINT AUTO_INCREMENT PRIMARY KEY, -- Auto-incrementing primary key
    parent_id BIGINT, -- Parent module ID
    module_name VARCHAR(255) NOT NULL, -- Module name (validated in application layer)
    created_by VARCHAR(255), -- Created by user
    modified_by VARCHAR(255), -- Modified by user
    ipaddress VARCHAR(255),
    status BOOLEAN NOT NULL DEFAULT TRUE, -- Status column, default true
    isdelete BOOLEAN NOT NULL DEFAULT FALSE, -- Is deleted flag, default false
    created_date DATETIME DEFAULT CURRENT_TIMESTAMP, -- Auto-set creation date
    modified_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP -- Auto-set modification date
);

CREATE TABLE designation_modules_master (
    id BIGINT AUTO_INCREMENT PRIMARY KEY, -- Auto-incrementing primary key
    created_by VARCHAR(255), -- Created by user
    modified_by VARCHAR(255), -- Modified by user
    ipaddress VARCHAR(255), -- IP address
    status BOOLEAN NOT NULL DEFAULT TRUE, -- Status flag (default true)
    isdelete BOOLEAN NOT NULL DEFAULT FALSE, -- Is deleted flag (default false)
    created_date DATETIME DEFAULT CURRENT_TIMESTAMP, -- Auto-set creation date
    modified_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- Auto-set modification date
    designations_id BIGINT, -- Foreign key to designations table
    modules_id BIGINT, -- Foreign key to modules table
     FOREIGN KEY (designations_id) REFERENCES designations_master(id), -- Foreign key constraint
     FOREIGN KEY (modules_id) REFERENCES modules_master(id) -- Foreign key constraint
);
CREATE TABLE user_master (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255),
    password VARCHAR(255) NOT NULL,
    f_name VARCHAR(255),
    l_name VARCHAR(255),
    m_name VARCHAR(255),
    ipaddres VARCHAR(255),
    timezone VARCHAR(255),
    mob_number VARCHAR(255),
    isdelete BOOLEAN,
    status BOOLEAN,
    created_by VARCHAR(255),
    modified_by VARCHAR(255),
    company_id BIGINT,
    role_id BIGINT,
    department_id BIGINT,
    designation_id BIGINT,
    created_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (company_id) REFERENCES company_master(id),
    FOREIGN KEY (role_id) REFERENCES Roles_Master(id),
    FOREIGN KEY (department_id) REFERENCES Departments_master(id),
    FOREIGN KEY (designation_id) REFERENCES designations_master(id)
);




INSERT INTO company_master (company_name, parent_id, company_address, company_email, company_country_code, company_phone, company_website, company_logo_path, ipaddress, status, isdelete, created_by, modified_by) 
VALUES ('Meldtecho', 0, '123 Business St City', 'meldtecho@gmail.com', 'US', '1234567890', 'www.meld.com', '[\"/path/to/logo1.png\", \"/path/to/logo2.png\"]', 
'192.168.1.1', TRUE, FALSE, 'admin', 'admin');

INSERT INTO roles_master (role_name, ipaddress, status, isdelete, created_by, modified_by)
VALUES ('Super Admin', '14.96.97.220', TRUE, FALSE, 'Meld Techo', 'Meld Techo');

INSERT INTO Departments_master (
    department_name, 
    created_by, 
    modified_by, 
    ipaddress, 
    status, 
    isdelete
) 
VALUES (
    'Admin',        -- Department name
    'Meld Techo',        -- Created by user
    'Meld Techo',        -- Modified by user
    '192.168.1.1',  -- IP address
    TRUE,           -- Status (active)
    FALSE           -- Soft delete flag
);

INSERT INTO designations_master (
    designation_name, 
    created_by, 
    modified_by, 
    ipaddress, 
    department_id, 
    status, 
    isdelete
) 
VALUES (
    'Admin Head',     -- Designation name
    'Meld Techo',                 -- Created by user
    'Meld Techo',                 -- Modified by user
    '192.168.1.2',           -- IP address
    1,                       -- Department ID (foreign key referencing departments_master table)
    TRUE,                    -- Status (active)
    FALSE                    -- Soft delete flag (not deleted)
);

INSERT INTO modules_master (
    parent_id, 
    module_name, 
    created_by, 
    modified_by, 
    ipaddress, 
    status, 
    isdelete
) 

    VALUES ( 0, 'Quickstart','Meld Techo','Meld Techo', '192.168.1.1', TRUE, FALSE ),
    ( 0, 'Dashboard','Meld Techo','Meld Techo', '192.168.1.1', TRUE, FALSE ),
    ( 0, 'Packages','Meld Techo','Meld Techo', '192.168.1.1', TRUE, FALSE ),
    ( 0, 'Masters','Meld Techo','Meld Techo', '192.168.1.1', TRUE, FALSE ),
    ( 3, 'New Packages','Meld Techo','Meld Techo', '192.168.1.1', TRUE, FALSE ),
    ( 3, 'package Dashboard','Meld Techo','Meld Techo', '192.168.1.1', TRUE, FALSE ),
    ( 4, 'New Country','Meld Techo','Meld Techo', '192.168.1.1', TRUE, FALSE ),
    ( 4, 'New States','Meld Techo','Meld Techo', '192.168.1.1', TRUE, FALSE ),
    ( 4, 'New City','Meld Techo','Meld Techo', '192.168.1.1', TRUE, FALSE ),
    ( 4, 'New Transport','Meld Techo','Meld Techo', '192.168.1.1', TRUE, FALSE ),
    ( 4, 'New Hotel','Meld Techo','Meld Techo', '192.168.1.1', TRUE, FALSE ),
    ( 4, 'New Vendor','Meld Techo','Meld Techo', '192.168.1.1', TRUE, FALSE ),
    ( 4, 'New Itinerary','Meld Techo','Meld Techo', '192.168.1.1', TRUE, FALSE ),
    ( 4, 'New policies','Meld Techo','Meld Techo', '192.168.1.1', TRUE, FALSE ),
    ( 0, 'Booking','Meld Techo','Meld Techo', '192.168.1.1', TRUE, FALSE ),
    ( 15, 'New Query','Meld Techo','Meld Techo', '192.168.1.1', TRUE, FALSE ),
    ( 15, 'Query Dashboard','Meld Techo','Meld Techo', '192.168.1.1', TRUE, FALSE ),
    ( 0, 'My Team','Meld Techo','Meld Techo', '192.168.1.1', TRUE, FALSE ),
    ( 18, 'New Members','Meld Techo','Meld Techo', '192.168.1.1', TRUE, FALSE ),
    ( 18, 'User Boards Dashboard','Meld Techo','Meld Techo', '192.168.1.1', TRUE, FALSE ),
     ( 0, 'Customers','Meld Techo','Meld Techo', '192.168.1.1', TRUE, FALSE ),
      ( 22, 'New Customer','Meld Techo','Meld Techo', '192.168.1.1', TRUE, FALSE ),
        ( 22, 'Customer Board','Meld Techo','Meld Techo', '192.168.1.1', TRUE, FALSE );

  INSERT INTO designation_modules_master (
    created_by, 
    modified_by, 
    ipaddress, 
    status, 
    isdelete, 
    designations_id, 
    modules_id
) 
VALUES (  'Meld Techo', 'Meld Techo', '192.168.1.1', TRUE,  FALSE, 1,1),
    (  'Meld Techo', 'Meld Techo', '192.168.1.1', TRUE,  FALSE, 1,2),
    (  'Meld Techo', 'Meld Techo', '192.168.1.1', TRUE,  FALSE, 1,3),
    (  'Meld Techo', 'Meld Techo', '192.168.1.1', TRUE,  FALSE, 1,4),
    (  'Meld Techo', 'Meld Techo', '192.168.1.1', TRUE,  FALSE, 1,5),
    (  'Meld Techo', 'Meld Techo', '192.168.1.1', TRUE,  FALSE, 1,6),
    (  'Meld Techo', 'Meld Techo', '192.168.1.1', TRUE,  FALSE, 1,7),
    (  'Meld Techo', 'Meld Techo', '192.168.1.1', TRUE,  FALSE, 1,8),
    (  'Meld Techo', 'Meld Techo', '192.168.1.1', TRUE,  FALSE, 1,9),
    (  'Meld Techo', 'Meld Techo', '192.168.1.1', TRUE,  FALSE, 1,10),
    (  'Meld Techo', 'Meld Techo', '192.168.1.1', TRUE,  FALSE, 1,11),
    (  'Meld Techo', 'Meld Techo', '192.168.1.1', TRUE,  FALSE, 1,12),
    (  'Meld Techo', 'Meld Techo', '192.168.1.1', TRUE,  FALSE, 1,13),
    (  'Meld Techo', 'Meld Techo', '192.168.1.1', TRUE,  FALSE, 1,14),
    (  'Meld Techo', 'Meld Techo', '192.168.1.1', TRUE,  FALSE, 1,15),
    (  'Meld Techo', 'Meld Techo', '192.168.1.1', TRUE,  FALSE, 1,16),
    (  'Meld Techo', 'Meld Techo', '192.168.1.1', TRUE,  FALSE, 1,17),
    (  'Meld Techo', 'Meld Techo', '192.168.1.1', TRUE,  FALSE, 1,18),
    (  'Meld Techo', 'Meld Techo', '192.168.1.1', TRUE,  FALSE, 1,19),
    (  'Meld Techo', 'Meld Techo', '192.168.1.1', TRUE,  FALSE, 1,20),
    (  'Meld Techo', 'Meld Techo', '192.168.1.1', TRUE,  FALSE, 1,21),
    (  'Meld Techo', 'Meld Techo', '192.168.1.1', TRUE,  FALSE, 1,22),
    (  'Meld Techo', 'Meld Techo', '192.168.1.1', TRUE,  FALSE, 1,23);

INSERT INTO user_master (
    email, 
    password, 
    f_name, 
    l_name, 
    m_name, 
    ipaddres, 
    timezone, 
    mob_number, 
    isdelete, 
    status, 
    created_by, 
    modified_by, 
    company_id, 
    role_id, 
    department_id, 
    designation_id
) VALUES (
    'narender@123gmail.com', 
     '123456', 
    'narender', 
    'singh', 
    'rajput', 
    '192.168.1.1', 
    'UTC', 
    '9813734234',
     TRUE,                    -- Status (active)
    FALSE,   
    'Super Admin', 
    'Super Admin', 
    1, 
    1, 
    1, 
    1
);























