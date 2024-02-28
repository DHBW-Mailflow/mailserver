package de.dhbw.karlsruhe.students.mailflow.core.application.imap.james;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.james.filesystem.api.FileSystem;

public class JamesFileSystem implements FileSystem {

    @Override
    public InputStream getResource(String url) throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getResource'");
    }

    @Override
    public File getFile(String fileURL) throws FileNotFoundException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFile'");
    }

    @Override
    public File getBasedir() throws FileNotFoundException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBasedir'");
    }

}
