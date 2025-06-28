-- CREAR BASE DE DATOS
DROP DATABASE IF EXISTS bd_cursos;
CREATE DATABASE bd_cursos;
USE bd_cursos;

-- TABLA categorias (opcional)
CREATE TABLE categorias (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    descripcion TEXT
);

-- TABLA cursos
CREATE TABLE cursos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    profesor_id BIGINT NOT NULL,
    horario_propuesto VARCHAR(100),
    fecha_inicio DATETIME,
    fecha_fin DATETIME,
    duracion_horas INT,
    vacantes INT NOT NULL,
    vacantes_disponibles INT NOT NULL,
    activo BOOLEAN DEFAULT TRUE,
    categoria_id BIGINT,
    FOREIGN KEY (categoria_id) REFERENCES categorias(id) ON DELETE SET NULL
);

-- TABLA horarios
CREATE TABLE horarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_horario VARCHAR(100) NOT NULL,
    usuario_id BIGINT NOT NULL,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- TABLA horario_detalle
CREATE TABLE horario_detalle (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    horario_id BIGINT NOT NULL,
    curso_id BIGINT NOT NULL,
    dia_semana VARCHAR(20),
    hora_inicio TIME,
    hora_fin TIME,
    FOREIGN KEY (horario_id) REFERENCES horarios(id) ON DELETE CASCADE,
    FOREIGN KEY (curso_id) REFERENCES cursos(id) ON DELETE CASCADE
);

-- TABLA inscripciones
CREATE TABLE inscripciones (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    curso_id BIGINT NOT NULL,
    fecha_inscripcion DATETIME DEFAULT CURRENT_TIMESTAMP,
    estado ENUM('ACTIVO', 'RETIRADO') DEFAULT 'ACTIVO',
    FOREIGN KEY (curso_id) REFERENCES cursos(id) ON DELETE CASCADE
);

-- TABLA preguntas (para el chatbot)
CREATE TABLE preguntas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario_id BIGINT,
    mensaje TEXT NOT NULL,
    respuesta TEXT,
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP
);
