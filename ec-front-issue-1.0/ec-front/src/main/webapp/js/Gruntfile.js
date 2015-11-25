module.exports = function(grunt) {
    // 配置
    grunt.initConfig({
        pkg : grunt.file.readJSON('package.json'),
        transport : {
            shoppingcart : {
                 options : {
                    idleading : 'v1/'
                },
                files : [
                    {
                        cwd : 'v1',
                        src : '**/*',
                        filter : 'isFile',
                        dest : 'v1/.build'
                    }
                ]
            }
        },
        concat : {
            shoppingcart : {
                options : {
                    include : 'all'
                },
                files: [
                    {
                        expand: true,
                        cwd: 'v1/.build/',
                        src: ['src/**/*.js','etc/**/*.js','!**/*-debug.js'],
                        dest: 'app/',
                        ext: '.js',
                        rename:function(filename){return 'v1/app/shoppingcart.js';}
                     }
                ]
            },
            debug : {
                options : {
                    include : 'all'
                },
                files: [
                    {
                        expand: true,
                        cwd: 'v1/.build/',
                        src: ['src/**/*-debug.js','etc/**/*-debug.js'],
                        dest: 'app/',
                        ext: '.js',
                        rename:function(filename){return 'v1/app/shoppingcart-debug.js';}
                     }
                ]
            }
        },
        uglify : {
            shoppingcart : {
                files: [
                    {
                        expand: true,
                        cwd: 'v1/app/',
                        src: ['*.js','!*-debug.js'],
                        dest: 'v1/app/',
                        ext: '.js'
                    }
                ]
            }
        },
        clean : {
            spm : ['v1/.build']
        }
    });
    grunt.loadNpmTasks('grunt-cmd-transport');
    grunt.loadNpmTasks('grunt-contrib-concat');
    grunt.loadNpmTasks('grunt-contrib-clean');
    grunt.loadNpmTasks('grunt-contrib-uglify');

    grunt.registerTask('build-shoppingcart', ['transport:shoppingcart', 'concat:shoppingcart', 'concat:debug', 'uglify:shoppingcart', 'clean']);
}; 
