terraform {
  required_providers {
    docker = {
      source  = "kreuzwerker/docker"
      version = "~> 3.0"
    }
  }
}

provider "docker" {}

resource "docker_image" "spring_app" {
  name         = "springboot-app"
  build {
    context    = "."
    dockerfile = "Dockerfile"
  }
}

resource "docker_container" "spring_app" {
  name  = "springboot-container"
  image = docker_image.spring_app.name

  ports {
    internal = 8080
    external = 8080
  }

  stdin_open = true
  tty        = true
}

