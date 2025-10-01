// 代码生成时间: 2025-10-02 01:45:29
 * It follows Java best practices for code structure, error handling, documentation, and maintainability.
 */

package com.example.animation;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import javax.inject.Singleton;

// Factory class to define beans for animation effects
@Factory
public class AnimationFactory {

    // Provides a bean for a fade animation effect
    @Bean
    @Singleton
    public AnimationEffect fadeAnimationEffect() {
        return new AnimationEffect("fade", true);
    }

    // Provides a bean for a slide animation effect
    @Bean
    @Singleton
    public AnimationEffect slideAnimationEffect() {
        return new AnimationEffect("slide", false);
    }
}

/*
 * AnimationEffect.java
 *
 * Represents an animation effect with a name and a boolean indicating if it's active or not.
 */
package com.example.animation;

public class AnimationEffect {

    private String name;
    private boolean isActive;

    // Constructor
    public AnimationEffect(String name, boolean isActive) {
        this.name = name;
        this.isActive = isActive;
    }

    // Gets the name of the animation effect
    public String getName() {
        return name;
    }

    // Sets the name of the animation effect
    public void setName(String name) {
        this.name = name;
    }

    // Checks if the animation effect is active
    public boolean isActive() {
        return isActive;
    }

    // Activates or deactivates the animation effect
    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
}

/*
 * AnimationController.java
 *
 * Provides endpoints to interact with animation effects.
 */
package com.example.animation;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.MediaType;
import io.micronaut.scheduling.TaskExecutors;
import java.util.concurrent.ExecutorService;

@Controller("/animations")
public class AnimationController {

    private final AnimationEffect fadeAnimationEffect;
    private final AnimationEffect slideAnimationEffect;

    // Dependency injection of animation effects
    public AnimationController(AnimationEffect fadeAnimationEffect, AnimationEffect slideAnimationEffect) {
        this.fadeAnimationEffect = fadeAnimationEffect;
        this.slideAnimationEffect = slideAnimationEffect;
    }

    // Endpoint to retrieve the status of fade animation effect
    @Get("/fade")
    public AnimationEffect getFadeAnimationEffect() {
        return fadeAnimationEffect;
    }

    // Endpoint to retrieve the status of slide animation effect
    @Get("/slide")
    public AnimationEffect getSlideAnimationEffect() {
        return slideAnimationEffect;
    }
}
