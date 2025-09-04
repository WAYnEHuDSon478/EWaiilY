// 代码生成时间: 2025-09-05 02:52:46
import io.micronaut.context.annotation.Requires;
    import io.micronaut.http.HttpResponse;
    import io.micronaut.http.annotation.Controller;
    import io.micronaut.http.annotation.Get;
    import io.micronaut.http.annotation.PathVariable;
    import java.io.File;
    import java.io.IOException;
    import java.nio.file.Files;
    import java.nio.file.Path;
    import java.nio.file.Paths;
    import java.util.List;
    import java.util.stream.Collectors;
    import javax.inject.Singleton;
    
    /**
     * Batch file renamer controller using Micronaut framework.
     */
    @Requires(env = 