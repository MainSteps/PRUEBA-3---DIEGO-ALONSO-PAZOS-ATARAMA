CREATE TABLE estado_reserva (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(200) NOT NULL,
    prioridad INT NOT NULL,
    activo BOOLEAN NOT NULL,
    fecha_creacion DATE NOT NULL
);

CREATE TABLE reserva (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cliente_id INT NOT NULL,
    vehiculo_id INT NOT NULL,
    codigo_reserva VARCHAR(100) NOT NULL,
    dias_arriendo INT NOT NULL,
    monto_total DOUBLE NOT NULL,
    confirmada BOOLEAN NOT NULL,
    fecha_inicio DATE NOT NULL,
    estado_reserva_id INT NOT NULL,
    CONSTRAINT fk_reserva_estado
        FOREIGN KEY (estado_reserva_id)
            REFERENCES estado_reserva(id)
);

INSERT INTO estado_reserva
(nombre, descripcion, prioridad, activo, fecha_creacion)
VALUES
    ('Pendiente', 'Reserva creada pero no confirmada', 1, true, '2026-05-14'),
    ('Confirmada', 'Reserva confirmada por el sistema', 2, true, '2026-05-14'),
    ('Cancelada', 'Reserva cancelada por el cliente', 3, true, '2026-05-14');

INSERT INTO reserva
(cliente_id, vehiculo_id, codigo_reserva, dias_arriendo, monto_total, confirmada, fecha_inicio, estado_reserva_id)
VALUES
    (1, 1, 'RES-001', 3, 135000, true, '2026-05-20', 2),
    (2, 2, 'RES-002', 2, 60000, true, '2026-05-22', 2),
    (3, 3, 'RES-003', 1, 60000, false, '2026-05-25', 1);