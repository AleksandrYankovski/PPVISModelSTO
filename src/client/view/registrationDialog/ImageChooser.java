package client.view.registrationDialog;

import javafx.scene.image.Image;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URI;
import java.util.List;

/**
 * Created by Александр on 22.09.2017.
 */
class ImageChooser {


    private final FileChooser chooser;
    private final List<FileChooser.ExtensionFilter> filters;
    private File file;


    public ImageChooser() {

        chooser = new FileChooser();

        filters = chooser.getExtensionFilters();

    }


    public Image openImage() {

        file = chooser.showOpenDialog(null);

        URI uri = file.toURI();
        return new Image(uri.toString());

    }

    public String getURI() {

        return file.toURI().toString();

    }


    public void setAvailableFormats(String... formats) {

        filters.clear(); // Удаляем все прошлые форматы.

        if (formats != null && formats.length > 0) { // Если есть что добавить.

            FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter(String.join(", ", formats), formats);


            filters.add(filter);

        }
    }
}
