    # Architecture du projet

    Le projet s’articule autour d’une hiérarchie de packages Java suivant le motif **MVC**.

    ## Diagramme de paquets

    ```mermaid
    graph TD
        org(models) --> org.controllers
        org.controllers --> org.views
        org.views -->|FXML| UI[UI]
    ```

    ## Statistiques

    - Nombre total de classes : **47**
    - **org.controllers** : 11 classes
- **org.views.popup** : 11 classes
- **org.views** : 9 classes
- **org.models** : 6 classes
- **org.views.popupfield** : 6 classes
- **org.controllers.exceptions** : 3 classes
- **org** : 1 classes
