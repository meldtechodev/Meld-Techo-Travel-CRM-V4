-- Create company_master table
CREATE TABLE company_master (
    id SERIAL PRIMARY KEY,
    company_name VARCHAR(100) NOT NULL,
    parent_id BIGINT NULL,
    company_address VARCHAR(255) NOT NULL,
    company_email VARCHAR(150),
    company_country_code VARCHAR(20),
    company_phone VARCHAR(15),
    company_website VARCHAR(255),
    company_logo_path JSON NULL,
    ipaddress VARCHAR(50),
    status BOOLEAN DEFAULT TRUE,
    isdelete BOOLEAN DEFAULT FALSE,
    created_by VARCHAR(50),
    modified_by VARCHAR(50),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Trigger function for updating modified_date
CREATE OR REPLACE FUNCTION update_modified_date() 
RETURNS TRIGGER AS $$
BEGIN
    NEW.modified_date = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Trigger on company_master table
CREATE TRIGGER update_company_modified_date
BEFORE UPDATE ON company_master
FOR EACH ROW
EXECUTE FUNCTION update_modified_date();

-- Create roles_master table
CREATE TABLE roles_master (
    id SERIAL PRIMARY KEY,
    role_name VARCHAR(255) UNIQUE NOT NULL,
    ipaddress VARCHAR(255),
    status BOOLEAN NOT NULL DEFAULT TRUE,
    isdelete BOOLEAN NOT NULL DEFAULT FALSE,
    created_by VARCHAR(255),
    modified_by VARCHAR(255),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Trigger on roles_master table
CREATE TRIGGER update_roles_modified_date
BEFORE UPDATE ON roles_master
FOR EACH ROW
EXECUTE FUNCTION update_modified_date();

-- Create departments_master table
CREATE TABLE departments_master (
    id SERIAL PRIMARY KEY,
    department_name VARCHAR(255) NOT NULL,
    created_by VARCHAR(255),
    modified_by VARCHAR(255),
    ipaddress VARCHAR(255),
    status BOOLEAN NOT NULL DEFAULT TRUE,
    isdelete BOOLEAN NOT NULL DEFAULT FALSE,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Trigger on departments_master table
CREATE TRIGGER update_departments_modified_date
BEFORE UPDATE ON departments_master
FOR EACH ROW
EXECUTE FUNCTION update_modified_date();

-- Create designations_master table
CREATE TABLE designations_master (
    id SERIAL PRIMARY KEY,
    designation_name VARCHAR(255) NOT NULL,
    created_by VARCHAR(255),
    modified_by VARCHAR(255),
    ipaddress VARCHAR(255),
    department_id BIGINT,
    status BOOLEAN NOT NULL DEFAULT TRUE,
    isdelete BOOLEAN NOT NULL DEFAULT FALSE,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (department_id) REFERENCES departments_master(id) ON DELETE SET NULL
);

-- Trigger on designations_master table
CREATE TRIGGER update_designations_modified_date
BEFORE UPDATE ON designations_master
FOR EACH ROW
EXECUTE FUNCTION update_modified_date();

-- Create modules_master table
CREATE TABLE modules_master (
    id SERIAL PRIMARY KEY,
    parent_id BIGINT,
    module_name VARCHAR(255) NOT NULL,
    created_by VARCHAR(255),
    modified_by VARCHAR(255),
    ipaddress VARCHAR(255),
    status BOOLEAN NOT NULL DEFAULT TRUE,
    isdelete BOOLEAN NOT NULL DEFAULT FALSE,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Trigger on modules_master table
CREATE TRIGGER update_modules_modified_date
BEFORE UPDATE ON modules_master
FOR EACH ROW
EXECUTE FUNCTION update_modified_date();

-- Create designation_modules_master table
CREATE TABLE designation_modules_master (
    id SERIAL PRIMARY KEY,
    created_by VARCHAR(255),
    modified_by VARCHAR(255),
    ipaddress VARCHAR(255),
    status BOOLEAN NOT NULL DEFAULT TRUE,
    isdelete BOOLEAN NOT NULL DEFAULT FALSE,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    designations_id BIGINT,
    modules_id BIGINT,
    FOREIGN KEY (designations_id) REFERENCES designations_master(id),
    FOREIGN KEY (modules_id) REFERENCES modules_master(id)
);

-- Trigger on designation_modules_master table
CREATE TRIGGER update_designation_modules_modified_date
BEFORE UPDATE ON designation_modules_master
FOR EACH ROW
EXECUTE FUNCTION update_modified_date();

-- Create user_master table
CREATE TABLE user_master (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255),
    password VARCHAR(255) NOT NULL,
    f_name VARCHAR(255),
    l_name VARCHAR(255),
    m_name VARCHAR(255),
    ipaddress VARCHAR(255),
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
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (company_id) REFERENCES company_master(id),
    FOREIGN KEY (role_id) REFERENCES roles_master(id),
    FOREIGN KEY (department_id) REFERENCES departments_master(id),
    FOREIGN KEY (designation_id) REFERENCES designations_master(id)
);

-- Trigger on user_master table
CREATE TRIGGER update_user_modified_date
BEFORE UPDATE ON user_master
FOR EACH ROW
EXECUTE FUNCTION update_modified_date();

-- Insert sample data into company_master
-- Insert sample data into company_master
INSERT INTO company_master (company_name, parent_id, company_address, company_email, company_country_code, company_phone, company_website, company_logo_path, ipaddress, status, isdelete, created_by, modified_by)
VALUES 
('Meldtecho', 0, '123 Business St City', 'meldtecho@gmail.com', 'US', '1234567890', 'www.meld.com', '["/path/to/logo1.png", "/path/to/logo2.png"]', '192.168.1.1', TRUE, FALSE, 'admin', 'admin');


-- Insert sample data into roles_master
INSERT INTO roles_master (role_name, ipaddress, status, isdelete, created_by, modified_by)
VALUES ('Super Admin', '14.96.97.220', TRUE, FALSE, 'Meld Techo', 'Meld Techo');

-- Insert sample data into departments_master
INSERT INTO departments_master (department_name, created_by, modified_by, ipaddress, status, isdelete)
VALUES ('Admin', 'Meld Techo', 'Meld Techo', '192.168.1.1', TRUE, FALSE);

-- Insert sample data into designations_master
INSERT INTO designations_master (designation_name, created_by, modified_by, ipaddress, department_id, status, isdelete)
VALUES ('Admin Head', 'Meld Techo', 'Meld Techo', '192.168.1.2', 1, TRUE, FALSE);

-- Insert sample data into modules_master
INSERT INTO modules_master (parent_id, module_name, created_by, modified_by, ipaddress, status, isdelete)
VALUES 
(0, 'Quickstart','Meld Techo','Meld Techo', '192.168.1.1', TRUE, FALSE),
(0, 'Dashboard','Meld Techo','Meld Techo', '192.168.1.1', TRUE, FALSE),
(0, 'Packages','Meld Techo','Meld Techo', '192.168.1.1', TRUE, FALSE),
(0, 'Masters','Meld Techo','Meld Techo', '192.168.1.1', TRUE, FALSE),
(3, 'New Packages','Meld Techo','Meld Techo', '192.168.1.1', TRUE, FALSE),
(3, 'Package Dashboard','Meld Techo','Meld Techo', '192.168.1.1', TRUE, FALSE),
(4, 'New Country','Meld Techo','Meld Techo', '192.168.1.1', TRUE, FALSE),
(4, 'New States','Meld Techo','Meld Techo', '192.168.1.1', TRUE, FALSE),
(4, 'New City','Meld Techo','Meld Techo', '192.168.1.1', TRUE, FALSE),
(4, 'New Transport','Meld Techo','Meld Techo', '192.168.1.1', TRUE, FALSE),
(4, 'New Hotel','Meld Techo','Meld Techo', '192.168.1.1', TRUE, FALSE),
(4, 'New Vendor','Meld Techo','Meld Techo', '192.168.1.1', TRUE, FALSE),
(4, 'New Itinerary','Meld Techo','Meld Techo', '192.168.1.1', TRUE, FALSE),
(4, 'New Policies','Meld Techo','Meld Techo', '192.168.1.1', TRUE, FALSE),
(0, 'Booking','Meld Techo','Meld Techo', '192.168.1.1', TRUE, FALSE),
(15, 'New Query','Meld Techo','Meld Techo', '192.168.1.1', TRUE, FALSE),
(15, 'Query Dashboard','Meld Techo','Meld Techo', '192.168.1.1', TRUE, FALSE),
(0, 'My Team','Meld Techo','Meld Techo', '192.168.1.1', TRUE, FALSE),
(18, 'New Members','Meld Techo','Meld Techo', '192.168.1.1', TRUE, FALSE),
(18, 'User Boards Dashboard','Meld Techo','Meld Techo', '192.168.1.1', TRUE, FALSE),
(0, 'Customers','Meld Techo','Meld Techo', '192.168.1.1', TRUE, FALSE),
(22, 'New Customer','Meld Techo','Meld Techo', '192.168.1.1', TRUE, FALSE),
(22, 'Customer Board','Meld Techo','Meld Techo', '192.168.1.1', TRUE, FALSE);


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
    ipaddress, 
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
     TRUE,                    
    FALSE,   
    'Super Admin', 
    'Super Admin', 
    1, 
    1, 
    1, 
    1
);