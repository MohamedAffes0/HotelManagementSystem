CREATE OR REPLACE FUNCTION get_available_rooms(
    p_start_date IN DATE,
    p_end_date   IN DATE,
    p_num_people IN NUMBER
) RETURN SYS_REFCURSOR
    IS
        available_rooms SYS_REFCURSOR;
    BEGIN
        OPEN available_rooms FOR
            SELECT id_chambre
            FROM chambre r
            WHERE r.nb_personnes >= p_num_people
                AND r.id_chambre NOT IN (
                    SELECT reservation.chambre
                    FROM reservation
                    WHERE (p_start_date BETWEEN reservation.date_debut AND reservation.date_fin)
                        OR (p_end_date BETWEEN reservation.date_debut AND reservation.date_fin)
                        OR (reservation.date_debut BETWEEN p_start_date AND p_end_date)
                        OR (reservation.date_fin BETWEEN p_start_date AND p_end_date)
                );

        RETURN available_rooms;
    END;
/
