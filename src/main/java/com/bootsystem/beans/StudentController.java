package com.bootsystem.beans;

import com.bootsystem.entities.Student;
import com.bootsystem.beans.util.JsfUtil;
import com.bootsystem.beans.util.PaginationHelper;
import com.bootsystem.controllers.StudentJpaController;
import com.bootsystem.entities.Folder;

import java.io.Serializable;
import java.util.ResourceBundle;
import jakarta.annotation.Resource;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.faces.model.DataModel;
import jakarta.faces.model.ListDataModel;
import jakarta.faces.model.SelectItem;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import jakarta.transaction.UserTransaction;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

@Named("studentController")
@ViewScoped
public class StudentController implements Serializable {

    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "my_persistence_unit")
    private EntityManagerFactory emf = null;

    private Student current;
    private DataModel items = null;
    private StudentJpaController jpaController = null;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    @Inject
    private FolderController folderController;

    public StudentController() {
    }

    public Student getSelected() {
        if (current == null) {
            current = new Student();
            selectedItemIndex = -1;
        }
        return current;
    }

    private StudentJpaController getJpaController() {
        if (jpaController == null) {
            jpaController = new StudentJpaController(utx, emf);
        }
        return jpaController;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getJpaController().getStudentCount();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getJpaController().findStudentEntities(getPageSize(), getPageFirstItem()));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List?faces-redirect=true";
    }

    public void prepareView() {
        current = (Student) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();

    }

    public String prepareCreate() {
        current = new Student();
        selectedItemIndex = -1;
        return "Create";
    }

    public void calculateAge() {
        // Primero verificar null
        if (current.getBirthday() == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Debe ingresar una fecha de nacimiento"));
            return;
        }
        // Luego verificar fecha futura
        if (current.getBirthday().isAfter(LocalDate.now())) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("La fecha de nacimiento no puede ser futura"));
            current.setAge(0); // Resetear edad si había valor previo
            return;
        }
        // Calcular edad
        long years = ChronoUnit.YEARS.between(current.getBirthday(), LocalDate.now());

        // Validar rango razonable (opcional)
        if (years > 120) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Por favor verifique la fecha de nacimiento"));
            current.setAge(0);
            return;
        }

        current.setAge((int) years);

    }

    public String create() {
        try {
            // Validación de unidad (obligatoria)
            if (current.getFkUnit() == null) {
                JsfUtil.addErrorMessage("El estudiante debe tener una unidad asignada.");
                return null;
            }

            // Inicializa la colección si es null
            if (current.getFolderCollection() == null) {
                current.setFolderCollection(new ArrayList<>());
            }

            // Crea el Folder y asigna la unidad actual del Student
            Folder folder = new Folder();
            folder.setCreated(LocalDate.now());
            folder.setFkStudent(current);
            folder.setFkUnit(current.getFkUnit()); // ¡Aquí asignamos la unidad!
            folder.setCode("Carpeta de " + current.getName() + " en " + current.getFkUnit().getName());

            // Añade a la colección (la cascada lo persistirá)
            current.getFolderCollection().add(folder);

            // Persiste el Student (y el Folder por cascada)
            current.setCreated(LocalDateTime.now());
            current.setStatus(Boolean.TRUE);
            getJpaController().create(current);

            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("student_created"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Error al guardar.");
            return null;
        }
    }

    public void prepareEdit() {
        current = (Student) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        
    }

    public String update() {
        try {
            getJpaController().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("student_update"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("error_persistence"));
            return null;
        }
    }

    public String destroy() {
        current = (Student) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getJpaController().destroy(current.getId());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("StudentDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("error_persistence"));
        }
    }

    private void updateCurrentItem() {
        int count = getJpaController().getStudentCount();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getJpaController().findStudentEntities(1, selectedItemIndex).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(getJpaController().findStudentEntities(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(getJpaController().findStudentEntities(), true);
    }

    @FacesConverter(forClass = Student.class)
    public static class StudentControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            StudentController controller = (StudentController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "studentController");
            return controller.getJpaController().findStudent(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Student) {
                Student o = (Student) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Student.class.getName());
            }
        }

    }

}
