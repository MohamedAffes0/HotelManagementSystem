CREATE OR REPLACE FUNCTION get_available_rooms(
    p_start_date IN DATE,--when the stay starts
    p_end_date   IN DATE,--when it ends
    p_num_people IN NUMBER --number of people concerned by the reservation
) RETURN SYS_REFCURSOR
    IS
        available_rooms SYS_REFCURSOR;--oracle cursor that returns the availbale rooms dynamically
    BEGIN
        OPEN available_rooms FOR
            SELECT id_chambre
            FROM chambre r
            --checking if the maximum occupancy is greater than the number of people pf the reservation
            WHERE r.nb_personnes >= p_num_people
                --checking if the room is available in that period
                AND r.id_chambre NOT IN (
                    --selecting the rooms reserved in that period 
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
