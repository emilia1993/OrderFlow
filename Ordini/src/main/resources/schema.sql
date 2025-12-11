CREATE TABLE testata_ordine (
    id INT AUTO_INCREMENT PRIMARY KEY,
    descrizione VARCHAR(255),
    data_consegna DATE,
    stato_ordine VARCHAR(50)
);

CREATE TABLE righe_ordine (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_ordine INT,
    cod_prodotto VARCHAR(50),
    prezzo DECIMAL(10,2),
    CONSTRAINT fk_righe_testata FOREIGN KEY (id_ordine)
        REFERENCES testata_ordine(id)
        ON DELETE CASCADE
);

CREATE TABLE pagamenti (
    id INT AUTO_INCREMENT PRIMARY KEY,
    data_pagamento DATE,
    id_ordine INT,
    CONSTRAINT fk_pagamenti_testata FOREIGN KEY (id_ordine)
        REFERENCES testata_ordine(id)
        ON DELETE CASCADE
);
