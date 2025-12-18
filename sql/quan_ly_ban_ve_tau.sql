DROP DATABASE IF EXISTS quan_ly_ban_ve_tau;
CREATE DATABASE IF NOT EXISTS quan_ly_ban_ve_tau DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE quan_ly_ban_ve_tau;

-- ==============================================================================
-- 👤 THÀNH VIÊN 1: ADMIN & HỆ THỐNG
-- Nhiệm vụ: Quản lý đăng nhập, nhân sự.
-- ==============================================================================

-- 1. Bảng Nhân viên
DROP TABLE IF EXISTS nhan_vien;
CREATE TABLE nhan_vien(
    id INT AUTO_INCREMENT PRIMARY KEY,
    ma_nhan_vien VARCHAR(20) NOT NULL UNIQUE,
    mat_khau VARCHAR(255) NOT NULL,
    ho_ten VARCHAR(100) NOT NULL,
    ngay_sinh DATE NOT NULL DEFAULT (CURRENT_DATE),
    gioi_tinh VARCHAR(20) NOT NULL,
    sdt VARCHAR(20) NOT NULL UNIQUE,
    email VARCHAR(100) UNIQUE, -- Dùng để reset pass hoặc liên hệ
    dia_chi VARCHAR(255) NOT NULL,
    vai_tro VARCHAR(20) DEFAULT 'NHANVIEN' COMMENT 'ADMIN hoặc NHANVIEN' -- Bổ sung để phân quyền
);

-- ==============================================================================
-- 👤 THÀNH VIÊN 2: QUẢN LÝ HẠ TẦNG (TÀU & TOA & GHẾ)
-- Nhiệm vụ: Quản lý tài sản vật lý. Tạo dữ liệu Tàu/Toa để sinh ra Ghế.
-- ==============================================================================

-- 2. Bảng Tàu (Đầu tàu)
DROP TABLE IF EXISTS tau;
CREATE TABLE tau(
    id INT AUTO_INCREMENT PRIMARY KEY,
    ma_tau VARCHAR(20) NOT NULL UNIQUE COMMENT 'VD: SE1, TN1',
    ten_tau VARCHAR(100) NOT NULL COMMENT 'VD: Tàu Thống Nhất SE1'
);

-- 3. Bảng Loại toa (Để định nghĩa hệ số giá)
DROP TABLE IF EXISTS loai_toa;
CREATE TABLE loai_toa(
    id INT AUTO_INCREMENT PRIMARY KEY,
    ten_loai VARCHAR(50) NOT NULL UNIQUE COMMENT 'VD: Ngồi mềm điều hòa, Giường nằm',
    he_so_gia DECIMAL(3, 2) DEFAULT 1.0
);

-- 4. Bảng Toa tàu (Gắn vào Tàu)
DROP TABLE IF EXISTS toa_tau;
CREATE TABLE toa_tau(
    id INT AUTO_INCREMENT PRIMARY KEY,
    ma_toa VARCHAR(20) NOT NULL COMMENT 'VD: Toa 1, Toa 2',
    id_tau INT NOT NULL,
    id_loai_toa INT NOT NULL,
    
    FOREIGN KEY (id_tau) REFERENCES tau(id) ON DELETE CASCADE,
    FOREIGN KEY (id_loai_toa) REFERENCES loai_toa(id),
    
    -- Một tàu không được có 2 toa trùng tên
    UNIQUE KEY unique_toa_trong_tau (ma_toa, id_tau)
);

-- 5. Bảng Ghế (Sản phẩm cuối cùng để bán - Được sinh tự động từ code Java)
DROP TABLE IF EXISTS ghe;
CREATE TABLE ghe(
    id INT AUTO_INCREMENT PRIMARY KEY,
    so_ghe VARCHAR(10) NOT NULL COMMENT 'VD: A1, B2',
    id_toa_tau INT NOT NULL,
    
    FOREIGN KEY (id_toa_tau) REFERENCES toa_tau(id) ON DELETE CASCADE,
    UNIQUE KEY unique_ghe_trong_toa (so_ghe, id_toa_tau)
);

-- ==============================================================================
-- 👤 THÀNH VIÊN 3: ĐIỀU HÀNH & LỊCH TRÌNH
-- Nhiệm vụ: Quản lý Ga, Tuyến đường và Lên lịch chạy tàu.
-- ==============================================================================

-- 6. Bảng Ga tàu
DROP TABLE IF EXISTS ga_tau;
CREATE TABLE ga_tau(
    id INT AUTO_INCREMENT PRIMARY KEY,
    ma_ga VARCHAR(20) NOT NULL UNIQUE COMMENT 'VD: HN, DN, SG',
    ten_ga VARCHAR(100) NOT NULL UNIQUE COMMENT 'VD: Ga Hà Nội',
    dia_chi VARCHAR(255),
    thanh_pho VARCHAR(255) NOT NULL
);

-- 7. Bảng Tuyến đường (Nối 2 ga với nhau & Quy định giá trần)
DROP TABLE IF EXISTS tuyen_duong;
CREATE TABLE tuyen_duong(
    id INT AUTO_INCREMENT PRIMARY KEY,
    ma_tuyen VARCHAR(20) NOT NULL UNIQUE COMMENT 'VD: HN-SG',
    ten_tuyen VARCHAR(100) NOT NULL,
    id_ga_di INT NOT NULL,
    id_ga_den INT NOT NULL,
    khoang_cach_km INT,
    gia_co_ban DECIMAL(10, 2) NOT NULL COMMENT 'Giá gốc chưa nhân hệ số',
    
    FOREIGN KEY (id_ga_di) REFERENCES ga_tau(id),
    FOREIGN KEY (id_ga_den) REFERENCES ga_tau(id),
    UNIQUE KEY unique_route (id_ga_di, id_ga_den)
);

-- 8. Bảng Lịch trình (Kết hợp Tàu của TV2 và Tuyến của TV3)
DROP TABLE IF EXISTS lich_trinh;
CREATE TABLE lich_trinh(
    id INT AUTO_INCREMENT PRIMARY KEY,
    ma_lich_trinh VARCHAR(20) UNIQUE,
    id_tau INT NOT NULL,
    id_tuyen_duong INT NOT NULL,
    ngay_di DATETIME NOT NULL,
    ngay_den DATETIME NOT NULL,
    trang_thai VARCHAR(20) DEFAULT 'Sắp chạy',
    
    FOREIGN KEY (id_tau) REFERENCES tau(id),
    FOREIGN KEY (id_tuyen_duong) REFERENCES tuyen_duong(id),
    
    -- Một tàu không thể khởi hành 2 chuyến cùng lúc
    UNIQUE KEY unique_tau_schedule (id_tau, ngay_di)
);

-- ==============================================================================
-- 👤 THÀNH VIÊN 4: BÁN VÉ & KHÁCH HÀNG
-- Nhiệm vụ: Thực hiện giao dịch bán vé, quản lý khách.
-- ==============================================================================

-- 9. Bảng Khách hàng
DROP TABLE IF EXISTS khach_hang;
CREATE TABLE khach_hang(
    id INT AUTO_INCREMENT PRIMARY KEY,
    cccd VARCHAR(20) UNIQUE,
    ho_ten VARCHAR(100) NOT NULL,
    ngay_sinh DATE DEFAULT (CURRENT_DATE),
    gioi_tinh VARCHAR(20) NOT NULL,
    sdt VARCHAR(20) NOT NULL UNIQUE,
    dia_chi VARCHAR(255) NOT NULL
);

-- 10. Bảng Vé tàu (Lưu giao dịch)
DROP TABLE IF EXISTS ve_tau;
CREATE TABLE ve_tau(
    id INT AUTO_INCREMENT PRIMARY KEY,
    ma_ve VARCHAR(50) NOT NULL UNIQUE,
    id_khach_hang INT NOT NULL,
    id_lich_trinh INT NOT NULL,
    id_ghe INT NOT NULL,
    id_nhan_vien INT, -- Người bán vé (Lấy từ SessionManager)
    
    ngay_dat DATETIME DEFAULT CURRENT_TIMESTAMP,
    gia_ve DECIMAL(10, 2) NOT NULL,
    trang_thai VARCHAR(20) DEFAULT 'Đã thanh toán',
    
    FOREIGN KEY (id_khach_hang) REFERENCES khach_hang(id),
    FOREIGN KEY (id_lich_trinh) REFERENCES lich_trinh(id),
    FOREIGN KEY (id_ghe) REFERENCES ghe(id),
    FOREIGN KEY (id_nhan_vien) REFERENCES nhan_vien(id),
    
    -- QUAN TRỌNG: 1 ghế trên 1 lịch trình chỉ được bán 1 lần
    UNIQUE KEY unique_booking (id_lich_trinh, id_ghe) 
);

-- ==============================================================================
-- 👤 THÀNH VIÊN 5: THỐNG KÊ & BÁO CÁO
-- Nhiệm vụ: Không tạo bảng mới. Chỉ SELECT dữ liệu từ các bảng trên (đặc biệt là ve_tau).
-- ==============================================================================